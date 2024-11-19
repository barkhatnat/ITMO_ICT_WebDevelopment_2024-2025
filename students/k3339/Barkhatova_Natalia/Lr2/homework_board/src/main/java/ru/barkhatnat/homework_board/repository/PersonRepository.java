package ru.barkhatnat.homework_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.barkhatnat.homework_board.domain.Person;

@NoRepositoryBean
public interface PersonRepository<T extends Person, ID> extends JpaRepository<T, ID> {
}