package com.example.Trabajo_Integrador_2024.repository;
import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

//    @Query("SELECT o FROM Odontologo o WHERE o.matricula = ?1")
    Odontologo findByMatricula (String matricula);
    Boolean existsByMatricula (String matricula);

}
