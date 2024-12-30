package ru.barkhatnat.cinema.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.barkhatnat.cinema.domain.Role;
import ru.barkhatnat.cinema.dto.create.RoleCreateDto;
import ru.barkhatnat.cinema.dto.regular.RoleDto;
import ru.barkhatnat.cinema.dto.update.RoleUpdateDto;
import ru.barkhatnat.cinema.mapper.RoleMapper;
import ru.barkhatnat.cinema.repository.RoleRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/roles")
@RequiredArgsConstructor
public class RoleRestController {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final ObjectMapper objectMapper;

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestBody UUID id) {
        roleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> findAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = roles.stream()
                .map(roleMapper::toRoleDto)
                .toList();
        return ResponseEntity.ok(roleDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable UUID id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        RoleDto roleDto = roleMapper.toRoleDto(roleOptional.orElse(null));
        return ResponseEntity.ok(roleDto);
    }


    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleCreateDto roleCreateDto) {
        Role role = roleMapper.toEntity(roleCreateDto);
        Role resultRole = roleRepository.save(role);
        RoleDto roleDto = roleMapper.toRoleDto(resultRole);
        return ResponseEntity.ok(roleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleUpdateDto> update(@PathVariable UUID id, @RequestBody RoleUpdateDto roleUpdateDto) {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        roleMapper.updateWithNull(roleUpdateDto, role);
        Role resultRole = roleRepository.save(role);
        return ResponseEntity.ok(roleMapper.toRoleUpdateDto(resultRole));
    }
}
