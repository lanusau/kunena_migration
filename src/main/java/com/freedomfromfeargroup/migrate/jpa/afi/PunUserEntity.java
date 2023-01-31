package com.freedomfromfeargroup.migrate.jpa.afi;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pun_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PunUserEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "realname")
    private String realname;

    @Column(name = "location")
    private String location;

    @Column(name = "registered")
    private Integer registered;

    @Column(name = "last_visit")
    private Integer lastVisit;

    @Column(name = "signature")
    private String signature;

    @Column(name = "num_posts")
    private Integer numPosts;
}
