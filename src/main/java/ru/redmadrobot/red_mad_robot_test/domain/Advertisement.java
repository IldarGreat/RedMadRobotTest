package ru.redmadrobot.red_mad_robot_test.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Entity
@Table(name = "advertisements")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Advertisement extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    @ColumnDefault("'ACTIVE'")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private Status status;

    @ManyToOne
    private User user;

    @Column
    private String photo;
}




