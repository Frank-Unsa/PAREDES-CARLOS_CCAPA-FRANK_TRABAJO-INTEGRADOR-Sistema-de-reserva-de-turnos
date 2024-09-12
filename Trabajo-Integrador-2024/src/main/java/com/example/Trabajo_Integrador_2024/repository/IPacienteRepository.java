package com.example.Trabajo_Integrador_2024.repository;
import com.example.Trabajo_Integrador_2024.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByDni (String dni);
    Boolean existsByDni (String dni);
}
