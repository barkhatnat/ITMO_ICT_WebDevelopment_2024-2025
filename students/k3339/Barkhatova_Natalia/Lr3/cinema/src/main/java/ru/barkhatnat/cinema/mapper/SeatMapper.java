package ru.barkhatnat.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.barkhatnat.cinema.dto.regular.SeatDto;
import ru.barkhatnat.cinema.dto.create.SeatCreateDto;
import ru.barkhatnat.cinema.domain.Seat;
import ru.barkhatnat.cinema.dto.update.SeatUpdateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RowMapper.class})
public interface SeatMapper {
    Seat toEntity(SeatCreateDto seatCreateDto);

    SeatDto toSeatDto(Seat seat);

    Seat toEntity(SeatDto seatDto);

    SeatCreateDto toSeatCreateDto(Seat seat);

    SeatUpdateDto toSeatUpdateDto(Seat seat);

    Seat updateWithNull(SeatUpdateDto seatUpdateDto, @MappingTarget Seat seat);
}