package com.example.Trabajo_Integrador_2024.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaAlta;

    //estoy mapeando la relación para que 1 paciente tenga 1 solo domicilio
    @OneToOne(cascade = CascadeType.ALL)
    private Domicilio domicilio;
    // orphanRemoval estoy elimnando Turnos huérfanos  automáticamente si se eliminan de la colección turnoSet
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Turno> turnoSet = new HashSet<>();


}
