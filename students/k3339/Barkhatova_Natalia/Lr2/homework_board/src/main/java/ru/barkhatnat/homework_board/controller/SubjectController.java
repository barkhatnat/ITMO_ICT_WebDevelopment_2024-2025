package ru.barkhatnat.homework_board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.barkhatnat.homework_board.domain.MyUser;
import ru.barkhatnat.homework_board.domain.Subject;
import ru.barkhatnat.homework_board.domain.Teacher;
import ru.barkhatnat.homework_board.exception.SubjectNotFoundException;
import ru.barkhatnat.homework_board.exception.TeacherNotFoundException;
import ru.barkhatnat.homework_board.repository.MyUserRepository;
import ru.barkhatnat.homework_board.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final MyUserRepository myUserRepository;

    @GetMapping
    public String getAllSubjects(Model model) {
        List<Subject> subjects = subjectRepository.findAll();
        model.addAttribute("subjects", subjects);
        return "subject/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject/form";
    }

    @PostMapping
    public String createSubject(@ModelAttribute Subject subject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<MyUser> teacher = myUserRepository.findByEmail(email);
        if (teacher.isPresent()) {
            subject.setTeacher((Teacher) teacher.get());
        } else {
            throw new TeacherNotFoundException(email);
        }


        subjectRepository.save(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
        model.addAttribute("subject", subject);
        return "subject/form";
    }

    @PostMapping("/edit")
    public String updateSubject(@ModelAttribute Subject subject) {
        Subject existingSubject = subjectRepository.findById(subject.getId()).get();
        existingSubject.setName(subject.getName());
        subjectRepository.save(existingSubject);
        return "redirect:/subjects";
    }


    @GetMapping("/{id}/delete")
    public String deleteSubject(@PathVariable UUID id) {
        subjectRepository.deleteById(id);
        return "redirect:/subjects";
    }

    @GetMapping("/{id}")
    public String getSubjectDetails(@PathVariable UUID id, Model model) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            return "subject/details";
        } else {
            return "redirect:/subjects";
        }
    }
}
