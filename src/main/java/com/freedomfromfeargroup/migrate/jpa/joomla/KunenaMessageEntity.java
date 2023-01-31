package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "joomla_kunena_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KunenaMessageEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "parent")
    private Integer parent;

    @Column(name = "thread")
    private Integer thread;

    @Column(name = "catid")
    private Integer catid;
    
    @Column(name = "name")
    private String name;

    @Column(name = "userid")
    private Integer userid;

    @Column(name = "subject")
    private String subject;

    @Column(name = "time")
    private Integer time;

    @Column(name = "ip")
    private String ip;

}
