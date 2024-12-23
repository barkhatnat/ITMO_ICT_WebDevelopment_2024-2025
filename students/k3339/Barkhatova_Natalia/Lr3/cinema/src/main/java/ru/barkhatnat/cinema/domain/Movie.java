package ru.barkhatnat.cinema.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, columnDefinition = "INTERVAL")
    private Duration duration;

    @Column(length = 512)
    private String description;

    @OneToMany
    private List<Session> sessions = new ArrayList<>();

    public Movie(String name, Duration duration, String description, List<Session> sessions) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.sessions = sessions;
    }
}
