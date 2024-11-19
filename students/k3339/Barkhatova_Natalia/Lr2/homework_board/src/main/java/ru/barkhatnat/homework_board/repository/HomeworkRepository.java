package ru.barkhatnat.homework_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.barkhatnat.homework_board.domain.Homework;

import java.util.UUID;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
}