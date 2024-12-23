package ru.barkhatnat.cinema.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Entity
@Getter
@Setter
@FieldNameConstants
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer capacity;

    @OneToMany(mappedBy = "hall")
    private List<Row> rows = new ArrayList<>();

    @OneToMany(mappedBy = "hall")
    private List<Session> sessions = new ArrayList<>();

    public Hall(String name, Integer capacity, List<Row> rows, List<Session> sessions) {
        this.name = name;
        this.capacity = capacity;
        this.rows = rows;
        this.sessions = sessions;
    }
}
