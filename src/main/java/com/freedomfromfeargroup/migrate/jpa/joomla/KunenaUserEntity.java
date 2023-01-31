package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "joomla_kunena_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KunenaUserEntity {
    @Id
    @Column(name = "userid")
    private Integer userId;

    @Column(name = "signature")
    private String signature;

    @Column(name = "posts")
    private Integer posts;

    @Column(name = "location")
    private String location;
}
