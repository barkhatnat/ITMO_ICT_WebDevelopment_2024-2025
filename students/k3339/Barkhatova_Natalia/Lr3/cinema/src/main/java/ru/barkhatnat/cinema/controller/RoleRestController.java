package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.cinema.dto.create.RoleCreateDto;
import ru.barkhatnat.cinema.dto.regular.RoleDto;
import ru.barkhatnat.cinema.dto.update.RoleUpdateDto;
import ru.barkhatnat.cinema.service.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleService roleService;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleCreateDto roleCreateDto) {
        return ResponseEntity.ok(roleService.create(roleCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleUpdateDto> update(@PathVariable UUID id, @RequestBody RoleUpdateDto roleUpdateDto) {
        return ResponseEntity.ok(roleService.update(id, roleUpdateDto));
    }
}