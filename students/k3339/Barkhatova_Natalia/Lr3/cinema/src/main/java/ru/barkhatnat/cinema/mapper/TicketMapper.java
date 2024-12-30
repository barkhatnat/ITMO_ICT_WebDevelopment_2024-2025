package ru.barkhatnat.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.barkhatnat.cinema.domain.Ticket;
import ru.barkhatnat.cinema.dto.create.TicketCreateDto;
import ru.barkhatnat.cinema.dto.regular.TicketDto;
import ru.barkhatnat.cinema.dto.update.TicketUpdateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, SessionMapper.class, SeatMapper.class})
public interface TicketMapper {
    TicketDto toTicketDto(Ticket ticket);

    Ticket toEntity(TicketCreateDto ticketCreateDto);

    Ticket toEntity(TicketDto ticketDto);

    TicketUpdateDto toTicketUpdateDto(Ticket ticket);

    Ticket updateWithNull(TicketUpdateDto ticketUpdateDto, @MappingTarget Ticket ticket);
}