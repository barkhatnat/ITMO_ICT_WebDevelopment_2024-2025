package ru.barkhatnat.homework_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.barkhatnat.homework_board.domain.Submission;

import java.util.UUID;
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
}