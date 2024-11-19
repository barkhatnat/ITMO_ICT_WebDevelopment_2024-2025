package ru.barkhatnat.homework_board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class Person {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String middleName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser myUser;
}