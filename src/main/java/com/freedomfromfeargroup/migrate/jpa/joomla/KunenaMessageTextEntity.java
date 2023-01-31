package com.freedomfromfeargroup.migrate.jpa.joomla;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "joomla_kunena_messages_text")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KunenaMessageTextEntity {
    @Id
    @Column(name = "mesid")
    private Integer mesid;

    @Column(name = "message")
    private String message;

}
