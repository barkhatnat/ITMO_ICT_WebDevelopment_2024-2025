package ru.barkhatnat.homework_board.exception;

import java.util.UUID;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(UUID id) {
        super(String.format("Teacher with ID %s not found", id));
    }
    public TeacherNotFoundException(String email) {
        super(String.format("Teacher with email %s not found", email));
    }
}
