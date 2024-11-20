package ru.barkhatnat.homework_board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends MyUser {

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

    public Student(String email, String password, Role role, String name, String lastName, String middleName, List<Homework> homeworks, List<Submission> submissions, Classroom classroom, List<Subject> subjects) {
        super(email, password, role, name, lastName, middleName);
        this.homeworks = homeworks;
        this.submissions = submissions;
        this.classroom = classroom;
        this.subjects = subjects;
    }
}
