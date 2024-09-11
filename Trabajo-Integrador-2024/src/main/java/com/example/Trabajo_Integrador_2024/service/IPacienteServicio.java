package com.example.Trabajo_Integrador_2024.service;


import com.example.Trabajo_Integrador_2024.entity.Paciente;

import java.util.List;

public interface IPacienteServicio {
    //CRUD - ABM
    Paciente guardar (Paciente paciente);
    Paciente buscarPorId(Long id);
    List<Paciente> listarTodos();
    Paciente actualizar(Paciente paciente);
    Boolean eliminar(Long id);
    Boolean existeDni(String dni);
}
