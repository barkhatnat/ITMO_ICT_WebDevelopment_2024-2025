package ru.barkhatnat.cinema.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.regular.UserDto;
import ru.barkhatnat.cinema.dto.update.UserUpdateDto;
import ru.barkhatnat.cinema.service.TicketService;
import ru.barkhatnat.cinema.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> userDtos = userService.findAll();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable UUID id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserUpdateDto> update(@PathVariable UUID id, @RequestBody UserUpdateDto userUpdateDto) {
        UserUpdateDto userUpdate = userService.update(id, userUpdateDto);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/admin/users")
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
