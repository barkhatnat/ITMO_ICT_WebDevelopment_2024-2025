package ru.barkhatnat.homework_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.barkhatnat.homework_board.domain.Homework;
import ru.barkhatnat.homework_board.domain.Submission;

import java.util.List;
import java.util.UUID;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, UUID> {
    @Query("SELECT h FROM Homework h JOIN h.classroom c JOIN c.teacher t WHERE t.id = :teacherId")
    List<Homework> findByTeacherId(@Param("teacherId") UUID teacherId);

    @Query("SELECT h FROM Homework h JOIN h.classroom c JOIN c.students st WHERE st.id = :studentId")
    List<Homework> findByStudentId(@Param("studentId") UUID studentId);
}