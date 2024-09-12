package com.example.Trabajo_Integrador_2024.repository;

import com.example.Trabajo_Integrador_2024.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Long> {
    @Query("SELECT t FROM Turno t WHERE t.odontologo.id = :odontologoId AND t.fecha = :fecha")
    Turno findByOdontologoAndFecha(@Param("odontologoId") Long odontologoId, @Param("fecha") LocalDate fecha);
    //Turno findByOdontologoAndFecha(Long odontologoId, LocalDate fecha);

    @Query("SELECT t FROM Turno t WHERE t.paciente.id = :pacienteId AND t.fecha = :fecha")
    Turno findByPacienteAndFecha(@Param("pacienteId") Long pacienteId, @Param("fecha") LocalDate fecha);
    //Turno findByPacienteAndFecha(Long pacienteId, LocalDate fecha);

}
