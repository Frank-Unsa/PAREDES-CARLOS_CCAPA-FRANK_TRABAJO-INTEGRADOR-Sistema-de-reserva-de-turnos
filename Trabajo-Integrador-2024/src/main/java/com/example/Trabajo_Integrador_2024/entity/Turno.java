package com.example.Trabajo_Integrador_2024.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FetchType.EAGER -> la entidad relacionada se carga de manera inmediata (eagerly)
    //cuando se recupera la entidad principal de la base de datos.

    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;
    @ManyToOne(fetch = FetchType.EAGER)
    private Odontologo odontologo;
    private LocalDate fecha;


}
