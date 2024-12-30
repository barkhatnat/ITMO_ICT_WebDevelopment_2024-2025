package ru.barkhatnat.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.barkhatnat.cinema.dto.create.RowCreateDto;
import ru.barkhatnat.cinema.dto.regular.RowDto;
import ru.barkhatnat.cinema.domain.Row;
import ru.barkhatnat.cinema.dto.update.RowUpdateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {HallMapper.class})
public interface RowMapper {
    RowDto toRowDto(Row row);

    Row toEntity(RowCreateDto rowCreateDto);

    Row toEntity(RowDto rowDto);

    RowCreateDto toRowCreateDto(Row row);

    RowUpdateDto toRowUpdateDto(Row row);

    Row updateWithNull(RowUpdateDto rowUpdateDto, @MappingTarget Row row);
}