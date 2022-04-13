package com.sprangu.spranguback.domain.user.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "REGISTERED_USER")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

}
