package ru.barkhatnat.cinema.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.barkhatnat.cinema.dto.create.UserCreateDto;
import ru.barkhatnat.cinema.domain.User;
import ru.barkhatnat.cinema.dto.regular.UserDto;
import ru.barkhatnat.cinema.dto.update.UserUpdateDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserMapper {
    UserDto toUserDto(User user);

    User toEntity(UserCreateDto userCreateDto);

    UserCreateDto toUserCreateDto(User user);

    User updateWithNull(UserCreateDto userCreateDto, @MappingTarget User user);

    User updateWithNull(UserDto userDto, @MappingTarget User user);

    UserUpdateDto toUserUpdateDto(User user);

    User updateWithNull(UserUpdateDto userUpdateDto, @MappingTarget User user);

    User toEntity(UserDto userDto);
}