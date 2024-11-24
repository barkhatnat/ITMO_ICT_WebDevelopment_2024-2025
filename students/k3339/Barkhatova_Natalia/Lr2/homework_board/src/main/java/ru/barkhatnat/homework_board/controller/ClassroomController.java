package ru.barkhatnat.homework_board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.homework_board.domain.*;
import ru.barkhatnat.homework_board.exception.ClassroomNotFoundException;
import ru.barkhatnat.homework_board.exception.TeacherNotFoundException;
import ru.barkhatnat.homework_board.repository.ClassroomRepository;
import ru.barkhatnat.homework_board.repository.MyUserRepository;
import ru.barkhatnat.homework_board.repository.StudentRepository;
import ru.barkhatnat.homework_board.repository.SubjectRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomRepository classroomRepository;
    private final MyUserRepository myUserRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public String getAllClassrooms(Model model) {
        List<Classroom> classrooms = classroomRepository.findAll();
        model.addAttribute("classrooms", classrooms);
        return "classroom/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        MyUser teacher = getCurrentUser();
        List<Subject> teacherSubjects = subjectRepository.findByTeacherEmail(teacher.getEmail());
        model.addAttribute("teacherSubjects", teacherSubjects);
        model.addAttribute("classroom", new Classroom());
        model.addAttribute("subject", new Subject());
        model.addAttribute("students", studentRepository.findAll());
        return "classroom/form";
    }

    @PostMapping
    public String createClassroom(@ModelAttribute Classroom classroom) {
        MyUser teacher = getCurrentUser();
        classroom.setTeacher((Teacher) teacher);
        for (Student student : classroom.getStudents()) {
            student.getClassrooms().add(classroom);
        }
        classroomRepository.save(classroom);
        return "redirect:/classrooms";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ClassroomNotFoundException(id));
        List<Subject> teacherSubjects = subjectRepository.findByTeacherEmail(classroom.getTeacher().getEmail());
        model.addAttribute("classroom", classroom);
        model.addAttribute("teacherSubjects", teacherSubjects);
        model.addAttribute("subject", new Subject());
        model.addAttribute("students", studentRepository.findAll());
        return "classroom/form";
    }

    @PostMapping("/edit")
    public String updateClassroom(@ModelAttribute Classroom classroom) {
        Classroom existingClassroom = classroomRepository.findById(classroom.getId()).get();
        existingClassroom.setName(classroom.getName());
        existingClassroom.setStudents(classroom.getStudents());
        classroomRepository.save(existingClassroom);
        return "redirect:/classrooms";
    }

    @GetMapping("/{id}/delete")
    public String deleteClassroom(@PathVariable UUID id) {
        classroomRepository.deleteById(id);
        return "redirect:/classrooms";
    }

    @GetMapping("/students/{id}/classroom")
    public String getClassroomDetails(@PathVariable UUID id, Model model) {
        List<Student> students = studentRepository.findByClassroomId(id);
        model.addAttribute("students", students);
        return "classroom/students";
    }

    private MyUser getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return myUserRepository.findByEmail(email)
                .orElseThrow(() -> new TeacherNotFoundException(email));
    }

}
