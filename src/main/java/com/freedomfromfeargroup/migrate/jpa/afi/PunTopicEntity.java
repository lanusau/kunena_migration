package com.freedomfromfeargroup.migrate.jpa.afi;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pun_topics")
@Getter
@Setter
public class PunTopicEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "poster")
    private String poster;

    @Column(name = "subject")
    private String subject;

    @Column(name = "posted")
    private Integer posted;

    @Column(name = "first_post_id")
    private Integer firstPostId;

    @Column(name = "last_post")
    private Integer lastPost;

    @Column(name = "last_post_id")
    private Integer lastPostId;

    @Column(name = "last_poster")
    private String lastPoster;

    @Column(name = "forum_id")
    private Integer forumId;

    @Column(name = "num_replies")
    private Integer numReplies;

    @Column(name = "moved_to")
    private Integer movedTo;
}