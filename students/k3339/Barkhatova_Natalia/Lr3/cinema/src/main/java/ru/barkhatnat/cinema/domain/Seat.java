package ru.barkhatnat.cinema.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import ru.barkhatnat.cinema.domain.enums.SeatType;
import ru.barkhatnat.cinema.domain.enums.TicketStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "row_id", nullable = false)
    private Row row;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType type;

    @OneToMany(mappedBy = "seat")
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "seat")
    private List<OccupiedSeats> occupiedSeats;

    public Seat(Row row, Integer number, SeatType type, List<Ticket> tickets) {
        this.row = row;
        this.number = number;
        this.type = type;
        this.tickets = tickets;
    }
}
