package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "joomla_kunena_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KunenaCategoryEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "numtopics")
    private Integer numTopics;

    @Column(name = "numposts")
    private Integer numPosts;

    @Column(name = "last_topic_id")
    private Integer lastTopicId;

    @Column(name = "last_post_id")
    private Integer lastPostId;

    @Column(name = "last_post_time")
    private Integer lastPostTime;
}
