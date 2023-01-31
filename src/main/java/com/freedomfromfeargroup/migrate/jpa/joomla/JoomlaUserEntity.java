package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "joomla_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoomlaUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "params")
    private String params;

    @Column(name = "registerdate")
    private LocalDateTime registerDate;

    @Column(name = "lastvisitdate")
    private LocalDateTime lastVisitDate;

    @Column(name = "requirereset")
    private Integer requireReset;

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @JoinTable(
            name="joomla_user_usergroup_map",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<JoomlaUserGroupEntity> groups;
}
