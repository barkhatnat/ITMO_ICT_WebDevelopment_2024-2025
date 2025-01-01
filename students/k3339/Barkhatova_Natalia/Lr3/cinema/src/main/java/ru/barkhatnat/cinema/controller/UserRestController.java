package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.UserCreateDto;
import ru.barkhatnat.cinema.dto.regular.UserDto;
import ru.barkhatnat.cinema.dto.update.UserUpdateDto;
import ru.barkhatnat.cinema.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> userDtos = userService.findAll();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserDto userDto = userService.create(userCreateDto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDto> update(@PathVariable UUID id, @RequestBody UserUpdateDto userUpdateDto) {
        UserUpdateDto userUpdate = userService.update(id, userUpdateDto);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
