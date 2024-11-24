package ru.barkhatnat.homework_board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.homework_board.domain.Classroom;
import ru.barkhatnat.homework_board.domain.Homework;
import ru.barkhatnat.homework_board.domain.MyUser;
import ru.barkhatnat.homework_board.exception.ClassroomNotFoundException;
import ru.barkhatnat.homework_board.exception.HomeworkNotFoundException;
import ru.barkhatnat.homework_board.exception.TeacherNotFoundException;
import ru.barkhatnat.homework_board.repository.ClassroomRepository;
import ru.barkhatnat.homework_board.repository.HomeworkRepository;
import ru.barkhatnat.homework_board.repository.MyUserRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/homeworks")
@RequiredArgsConstructor
public class HomeworkController {

    private final HomeworkRepository homeworkRepository;
    private final ClassroomRepository classroomRepository;
    private final MyUserRepository myUserRepository;

    @GetMapping
    public String getAllHomeworks(Model model) {
        List<Homework> homeworks = homeworkRepository.findAll();
        model.addAttribute("homeworks", homeworks);
        return "homework/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("homework", new Homework());
        MyUser teacher = getCurrentUser();
        List<Classroom> teacherClasses = classroomRepository.findByTeacherEmail(teacher.getEmail());

        model.addAttribute("teacherClasses", teacherClasses);
        model.addAttribute("classroom", new Classroom());
        return "homework/form";
    }

    @PostMapping
    public String createHomework(@ModelAttribute Homework homework) {
        if (homework.getClassroom() == null || homework.getClassroom().getId() == null) {
            throw new IllegalArgumentException("Classroom ID is required");
        }
        UUID classroomId = homework.getClassroom().getId();
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ClassroomNotFoundException(classroomId));
        homework.setClassroom(classroom);
        homework.setActive(true);
        homeworkRepository.save(homework);

        return "redirect:/homeworks";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new HomeworkNotFoundException(id));
        List<Classroom> teacherClasses = classroomRepository.findByTeacherEmail(homework.getClassroom().getTeacher().getEmail());
        model.addAttribute("homework", homework);
        model.addAttribute("teacherClasses", teacherClasses);
        return "homework/form";
    }

    @PostMapping("/edit")
    public String updateHomework(@ModelAttribute Homework homework) {
        Homework existingHomework = homeworkRepository.findById(homework.getId()).get();
        existingHomework.setActive(homework.isActive());
        existingHomework.setDescription(homework.getDescription());
        existingHomework.setClassroom(homework.getClassroom());
        existingHomework.setMaxGrade(homework.getMaxGrade());
        existingHomework.setNumber(homework.getNumber());
        existingHomework.setExecutionPeriod(homework.getExecutionPeriod());
        existingHomework.setPenaltyInfo(homework.getPenaltyInfo());
        existingHomework.setDueDate(homework.getDueDate());
        homeworkRepository.save(existingHomework);
        return "redirect:/classrooms";
    }

    @GetMapping("/{id}/delete")
    public String deleteHomework(@PathVariable UUID id) {
        homeworkRepository.deleteById(id);
        return "redirect:/homeworks";
    }

    private MyUser getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return myUserRepository.findByEmail(email)
                .orElseThrow(() -> new TeacherNotFoundException(email));
    }
}

