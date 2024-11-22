package ru.barkhatnat.homework_board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends MyUser {

    @ManyToMany
    private List<Homework> homeworks;

    @ManyToMany(mappedBy = "students")
    private List<Classroom> classrooms;

    @OneToMany(mappedBy = "student")
    private List<Submission> submissions;

    public Student(String email, String password, Role role, String name, String lastName, String middleName, List<Homework> homeworks, List<Submission> submissions, List<Classroom> classrooms) {
        super(email, password, role, name, lastName, middleName);
        this.homeworks = homeworks;
        this.submissions = submissions;
        this.classrooms = classrooms;
    }
}
