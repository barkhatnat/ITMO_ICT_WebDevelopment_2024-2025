package ru.barkhatnat.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.barkhatnat.cinema.dto.update.SessionUpdateDto;
import ru.barkhatnat.cinema.domain.Session;
import ru.barkhatnat.cinema.dto.create.SessionCreateDto;
import ru.barkhatnat.cinema.dto.regular.SessionDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MovieMapper.class, HallMapper.class})
public interface SessionMapper {
    SessionDto toSessionDto(Session session);

    Session toEntity(SessionDto sessionDto);

    SessionCreateDto toSessionCreateDto(Session session);

    SessionUpdateDto toSessionUpdateDto(Session session);

    Session updateWithNull(SessionUpdateDto sessionUpdateDto, @MappingTarget Session session);
}