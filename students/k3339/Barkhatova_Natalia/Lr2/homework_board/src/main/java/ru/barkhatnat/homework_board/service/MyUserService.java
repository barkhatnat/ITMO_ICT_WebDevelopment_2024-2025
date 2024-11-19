package ru.barkhatnat.homework_board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.barkhatnat.homework_board.domain.MyUser;
import ru.barkhatnat.homework_board.exception.UserAlreadyExistsException;
import ru.barkhatnat.homework_board.repository.MyUserRepository;

@Service
@RequiredArgsConstructor
public class MyUserService {
    private final MyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MyUser createUser(MyUser myUser) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(myUser.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + myUser.getEmail() + " already exists");
        }
        String encodedPassword = passwordEncoder.encode(myUser.getPassword());
        MyUser user = userRepository.save(new MyUser(myUser.getEmail(), encodedPassword, myUser.getRole()));
        return user;
    }
}
