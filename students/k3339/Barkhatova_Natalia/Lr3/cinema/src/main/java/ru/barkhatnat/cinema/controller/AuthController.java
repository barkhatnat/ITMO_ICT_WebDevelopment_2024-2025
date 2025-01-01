package ru.barkhatnat.cinema.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.barkhatnat.cinema.domain.security.LoginRequest;
import ru.barkhatnat.cinema.domain.security.LoginResponse;
import ru.barkhatnat.cinema.dto.create.UserCreateDto;
import ru.barkhatnat.cinema.dto.regular.UserDto;
import ru.barkhatnat.cinema.security.CustomUserDetails;
import ru.barkhatnat.cinema.security.JwtIssuer;
import ru.barkhatnat.cinema.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> createAccount(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto userResponseDto = userService.create(userCreateDto);
        return ResponseEntity.ok().body(userResponseDto);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        List<String> roles = customUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = jwtIssuer.issue(customUserDetails.getUser().getId(), customUserDetails.getUser().getEmail(), roles);
        return LoginResponse.builder().accessToken(token).build();
    }
}
