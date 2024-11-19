package ru.barkhatnat.homework_board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Student extends Person {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToMany
    private List<Homework> homeworks;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Subject> subjects;

    @ManyToOne
    private Classroom classroom;

    @OneToMany(mappedBy = "student")
    private List<Submission> submissions;
}
