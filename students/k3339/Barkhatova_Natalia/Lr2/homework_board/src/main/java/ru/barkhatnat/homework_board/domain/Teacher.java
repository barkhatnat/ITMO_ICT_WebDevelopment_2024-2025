package ru.barkhatnat.homework_board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Teacher extends Person {
    @Id
    @UuidGenerator
    private UUID id;

    @OneToMany(mappedBy = "teacher")
    private List<Subject> subjects;
}
