package ru.barkhatnat.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.barkhatnat.cinema.domain.Hall;
import ru.barkhatnat.cinema.dto.create.HallCreateDto;
import ru.barkhatnat.cinema.dto.regular.HallDto;
import ru.barkhatnat.cinema.dto.update.HallUpdateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HallMapper {
    Hall toEntity(HallDto hallDto);

    HallDto toHallDto(Hall hall);

    Hall updateWithNull(HallDto hallDto, @MappingTarget Hall hall);

    Hall toEntity(HallCreateDto hallCreateDto);

    HallCreateDto toHallCreateDto(Hall hall);

    HallUpdateDto toHallUpdateDto(Hall hall);

    Hall updateWithNull(HallUpdateDto hallUpdateDto, @MappingTarget Hall hall);
}