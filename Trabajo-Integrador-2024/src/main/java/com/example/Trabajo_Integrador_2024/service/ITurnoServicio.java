package com.example.Trabajo_Integrador_2024.service;

import com.example.Trabajo_Integrador_2024.entity.Turno;

import java.util.List;

public interface ITurnoServicio {
    Turno guardar(Turno turno);
    Turno buscarPorId(Long id);
    List<Turno> listarTodos();
    Turno actualizar(Turno turno);
    void eliminar(Long id);
}
