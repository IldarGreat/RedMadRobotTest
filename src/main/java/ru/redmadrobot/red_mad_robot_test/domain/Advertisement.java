package ru.redmadrobot.red_mad_robot_test.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "advertisements")
@Data
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
    private Status status;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] photo;
}




