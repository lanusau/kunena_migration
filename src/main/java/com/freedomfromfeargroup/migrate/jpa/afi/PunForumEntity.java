package com.freedomfromfeargroup.migrate.jpa.afi;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pun_forums")
@Getter
@Setter
public class PunForumEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "forum_name")
    private String forumName;

    @Column(name = "forum_desc")
    private String forumDesc;

    @Column(name = "num_topics")
    private Integer numTopics;

    @Column(name = "num_posts")
    private Integer numPosts;

    @Column(name = "last_post")
    private Integer lastPost;

    @Column(name = "last_post_id")
    private Integer lastPostId;

    @Column(name = "last_poster")
    private String lastPoster;

    @Column(name = "cat_id")
    private Integer catId;
}