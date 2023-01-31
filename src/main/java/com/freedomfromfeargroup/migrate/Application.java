package com.freedomfromfeargroup.migrate;

import com.freedomfromfeargroup.migrate.jpa.afi.*;
import com.freedomfromfeargroup.migrate.jpa.joomla.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
@Slf4j
public class Application implements ApplicationRunner {

	@Autowired
	private PunUserRepository punUserRepository;
	@Autowired
	private PunForumRepository punForumRepository;
	@Autowired
	private PunTopicRepository punTopicRepository;
	@Autowired
	private PunPostRepository punPostRepository;
	@Autowired
	private JoomlaUserRepository joomlaUserRepository;
	@Autowired
	private KunenaUserRepository kunenaUserRepository;
	@Autowired
	private KunenaCategoryRepository kunenaCategoryRepository;
	@Autowired
	private JoomlaUserGroupRepository joomlaUserGroupRepository;
	@Autowired
	private KunenaTopicRepository kunenaTopicRepository;
	@Autowired
	private KunenaMessageRepository kunenaMessageRepository;
	@Autowired
	private KunenaMessageTextRepository kunenaMessageTextRepository;

	private final Map<String, JoomlaUserEntity> userCache = new HashMap<>(3000);
	private JoomlaUserGroupEntity guest;
	private JoomlaUserGroupEntity veteran;
	private JoomlaUserGroupEntity registered;
	private JoomlaUserGroupEntity administrator;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		List<JoomlaUserGroupEntity> groups = joomlaUserGroupRepository.findAll();
		guest = groups.stream().filter(g -> g.getTitle().equals("Guest")).findFirst().orElse(null);
		veteran = groups.stream().filter(g -> g.getTitle().equals("Veteran")).findFirst().orElse(null);
		registered = groups.stream().filter(g -> g.getTitle().equals("Registered")).findFirst().orElse(null);
		administrator = groups.stream().filter(g -> g.getTitle().equals("Administrator")).findFirst().orElse(null);


		final List<String> migrations = args.getOptionValues("migrate");

		if (migrations.contains("users")) {
			migrateUsers();
		}

		if (migrations.contains("forum")) {
			migrateForum(args);
		}

	}

	private void migrateForum(ApplicationArguments args) {
		final List<String> forumNames = args.getOptionValues("name");
		for (String forumName : forumNames) {
			final PunForumEntity punForumEntity = punForumRepository.getByForumName(forumName).orElse(null);
			if (punForumEntity == null) {
				log.error("Forum {} not found in pub_forums", forumName);
				break;
			}
			final KunenaCategoryEntity kunenaCategoryEntity = kunenaCategoryRepository.getByName(forumName).orElse(null);
			if (kunenaCategoryEntity == null) {
				log.error("Forum {} not found in joomla_kunena_categories", forumName);
				break;
			}

			log.info("== Category {} ==", kunenaCategoryEntity.getName());

			int numTopics = 0;
			int numPosts = 0;
			int lastTopicId = 0;
			int lastPostId = 0;
			int lastPostTime = 0;

			for (PunTopicEntity punTopicEntity : punTopicRepository.findAllByForumId(punForumEntity.getId())) {
				if (punTopicEntity.getMovedTo() != null) continue; // Moved to another topic

				log.info("Migrating topic {}", punTopicEntity.getSubject());

				final KunenaTopicEntity kunenaTopicEntity = KunenaTopicEntity.builder()
						.id(punTopicEntity.getId())
						.categoryId(kunenaCategoryEntity.getId())
						.subject(punTopicEntity.getSubject())
						.posts(punTopicEntity.getNumReplies())
						.params("")
						.build();

				boolean firstPost = true;
				KunenaMessageEntity lastPost = null;
				String lastPostMessage = null;

				for (PunPostEntity punPostEntity : punPostRepository.findAllByTopicId(punTopicEntity.getId(), Sort.by("id"))) {

					final Integer parent = firstPost ? 0 : lastPost.getId();
					final String poster = punPostEntity.getPoster();
					final JoomlaUserEntity joomlaUserEntity = getOrCreateUser(poster);

					final KunenaMessageEntity kunenaMessageEntity = KunenaMessageEntity.builder()
							.id(punPostEntity.getId())
							.parent(parent)
							.thread(kunenaTopicEntity.getId())
							.catid(kunenaCategoryEntity.getId())
							.name(poster)
							.userid(joomlaUserEntity.getId())
							.subject(kunenaTopicEntity.getSubject())
							.time(punPostEntity.getPosted())
							.ip(punPostEntity.getPosterIp())
							.build();
					kunenaMessageRepository.save(kunenaMessageEntity);
					numPosts++;

					final String message = Util.convertMessage(punPostEntity.getMessage());

					final KunenaMessageTextEntity kunenaMessageTextEntity = KunenaMessageTextEntity.builder()
							.mesid(kunenaMessageEntity.getId())
							.message(message)
							.build();
					kunenaMessageTextRepository.save(kunenaMessageTextEntity);

					if (firstPost) {
						kunenaTopicEntity.setFirstPostId(kunenaMessageEntity.getId());
						kunenaTopicEntity.setFirstPostTime(kunenaMessageEntity.getTime());
						kunenaTopicEntity.setFirstPostUserid(kunenaMessageEntity.getUserid());
						kunenaTopicEntity.setFirstPostMessage(message);
					}

					firstPost = false;
					lastPost = kunenaMessageEntity;
					lastPostMessage = message;
				}

				if (lastPost != null) {
					kunenaTopicEntity.setLastPostId(lastPost.getId());
					kunenaTopicEntity.setLastPostTime(lastPost.getTime());
					kunenaTopicEntity.setLastPostUserid(lastPost.getUserid());
					kunenaTopicEntity.setLastPostMessage(lastPostMessage);

					lastPostId = lastPost.getId();
					lastPostTime = lastPost.getTime();
				}

				lastTopicId = kunenaTopicEntity.getId();

				kunenaTopicRepository.save(kunenaTopicEntity);
				numTopics++;
			}

			kunenaCategoryEntity.setNumTopics(numTopics);
			kunenaCategoryEntity.setNumPosts(numPosts);
			kunenaCategoryEntity.setLastTopicId(lastTopicId);
			kunenaCategoryEntity.setLastPostId(lastPostId);
			kunenaCategoryEntity.setLastPostTime(lastPostTime);
			kunenaCategoryRepository.save(kunenaCategoryEntity);
		}

	}

	private void migrateUsers() {

		for (PunUserEntity punUser : punUserRepository.findAll(Sort.by("id"))) {
			if (punUser.getId() == 1) continue; // Skip guest

			if (punUser.getUsername().equals("admin")) continue; // Skip admin

			if (joomlaUserRepository.existsByUsername(punUser.getUsername())) {
				log.warn("Duplicate username {}", punUser.getUsername());
				continue;
			}

			log.info("Migrating user {}", punUser.getUsername());

			final Set<JoomlaUserGroupEntity> userGroups = new HashSet<>();

				/*
				g_id g_title
				---- -----------------
				1    Administrators
				2    Moderators
				3    Guest
				4    Members
				5    Read Only
				6    New Registrations
				7    Veterans
				8    Success Only
				 */
			switch (punUser.getGroupId()) {
				case 1,2 -> {
					userGroups.add(administrator);
					userGroups.add(registered);
				}
				case 3,5,6,8 -> {
					userGroups.add(guest);
				}
				case 4 -> {
					userGroups.add(registered);
				}
				case 7 -> {
					userGroups.add(registered);
					userGroups.add(veteran);
				}
				default -> {}
			}

			final String email;
			if (StringUtils.hasText(punUser.getEmail())) {
				email = punUser.getEmail();
			} else {
				email = punUser.getUsername() + "@unknown.com";
			}

			final JoomlaUserEntity joomlaUser = JoomlaUserEntity.builder()
					.id(punUser.getId())
					.name(Optional.ofNullable(punUser.getRealname()).orElse(punUser.getUsername()))
					.username(punUser.getUsername())
					.email(email)
					.password("")
					.registerDate(Util.toLocalDateTime(punUser.getRegistered()))
					.lastVisitDate(Util.toLocalDateTime(punUser.getLastVisit()))
					.requireReset(1)
					.groups(userGroups)
					.params("")
					.build();
			joomlaUserRepository.save(joomlaUser);

			final KunenaUserEntity kunenaUser = KunenaUserEntity.builder()
					.userId(punUser.getId())
					.signature(punUser.getSignature())
					.posts(punUser.getNumPosts())
					.location(punUser.getLocation())
					.build();
			kunenaUserRepository.save(kunenaUser);

		}
	}

	private JoomlaUserEntity getOrCreateUser(String username) {

		JoomlaUserEntity joomlaUserEntity = userCache.get(username);
		if (joomlaUserEntity != null) {
			return joomlaUserEntity;
		}
		joomlaUserEntity = joomlaUserRepository.findByUsername(username).orElse(null);
		if (joomlaUserEntity != null) {
			userCache.put(username, joomlaUserEntity);
			return joomlaUserEntity;
		}

		final JoomlaUserEntity joomlaUser = JoomlaUserEntity.builder()
				.name(username)
				.username(username)
				.email(username+ "@unknown.com")
				.password("")
				.registerDate(LocalDateTime.now())
				.lastVisitDate(LocalDateTime.of(2006, 1, 1, 0, 0))
				.requireReset(1)
				.groups(Set.of(guest))
				.params("")
				.build();
		joomlaUserRepository.save(joomlaUser);

		return joomlaUser;
	}
}
