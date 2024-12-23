package ru.barkhatnat.cinema.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Row {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @OneToMany(mappedBy = "row")
    private List<Seat> seats = new ArrayList<>();

    public Row(Integer number, Hall hall, List<Seat> seats) {
        this.number = number;
        this.hall = hall;
        this.seats = seats;
    }
}
