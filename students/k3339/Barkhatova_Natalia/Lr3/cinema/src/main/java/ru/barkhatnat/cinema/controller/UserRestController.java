package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.User;
import ru.barkhatnat.cinema.dto.create.UserCreateDto;
import ru.barkhatnat.cinema.dto.regular.UserDto;
import ru.barkhatnat.cinema.dto.update.UserUpdateDto;
import ru.barkhatnat.cinema.mapper.UserMapper;
import ru.barkhatnat.cinema.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ObjectMapper objectMapper;


    @GetMapping
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        UserDto userDto = userMapper.toUserDto(userOptional.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id %s not found".formatted(id))
        ));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        User resultUser = userRepository.save(user);
        UserDto userDto = userMapper.toUserDto(resultUser);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDto> update(@PathVariable UUID id, @RequestBody UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        userMapper.updateWithNull(userUpdateDto, user);
        User resultUser = userRepository.save(user);
        return ResponseEntity.ok(userMapper.toUserUpdateDto(resultUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

