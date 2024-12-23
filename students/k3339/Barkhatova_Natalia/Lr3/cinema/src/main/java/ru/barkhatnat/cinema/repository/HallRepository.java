package ru.barkhatnat.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.barkhatnat.cinema.domain.Hall;

import java.util.UUID;

public interface HallRepository extends JpaRepository<Hall, UUID> , JpaSpecificationExecutor<Hall> {
}