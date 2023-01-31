package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "joomla_kunena_topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KunenaTopicEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "category_id")
    private Integer categoryId;
    
    @Column(name = "subject")
    private String subject;

    @Column(name = "posts")
    private Integer posts;

    @Column(name = "first_post_id")
    private Integer firstPostId;

    @Column(name = "first_post_time")
    private Integer firstPostTime;

    @Column(name = "first_post_userid")
    private Integer firstPostUserid;

    @Column(name = "first_post_message")
    private String firstPostMessage;

    @Column(name = "last_post_id")
    private Integer lastPostId;

    @Column(name = "last_post_time")
    private Integer lastPostTime;

    @Column(name = "last_post_userid")
    private Integer lastPostUserid;

    @Column(name = "last_post_message")
    private String lastPostMessage;

    @Column(name = "params")
    private String params;
}
