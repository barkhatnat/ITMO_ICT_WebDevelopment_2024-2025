package ru.barkhatnat.homework_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.barkhatnat.homework_board.domain.Classroom;

import java.util.UUID;
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
}