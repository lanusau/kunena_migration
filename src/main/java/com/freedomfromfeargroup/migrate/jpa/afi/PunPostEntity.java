package com.freedomfromfeargroup.migrate.jpa.afi;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pun_posts")
@Getter
@Setter
public class PunPostEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "poster")
    private String poster;

    @Column(name = "poster_id")
    private Integer posterId;

    @Column(name = "poster_ip")
    private String posterIp;

    @Column(name = "message")
    private String message;

    @Column(name = "posted")
    private Integer posted;

    @Column(name = "topic_id")
    private Integer topicId;
}