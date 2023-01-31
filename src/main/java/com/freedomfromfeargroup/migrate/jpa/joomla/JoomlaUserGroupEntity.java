package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "joomla_usergroups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoomlaUserGroupEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;
}
