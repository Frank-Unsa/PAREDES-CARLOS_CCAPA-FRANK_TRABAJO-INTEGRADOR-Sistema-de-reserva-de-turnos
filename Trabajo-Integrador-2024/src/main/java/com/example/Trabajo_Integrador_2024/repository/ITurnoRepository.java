package com.example.Trabajo_Integrador_2024.repository;

import com.example.Trabajo_Integrador_2024.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
}
