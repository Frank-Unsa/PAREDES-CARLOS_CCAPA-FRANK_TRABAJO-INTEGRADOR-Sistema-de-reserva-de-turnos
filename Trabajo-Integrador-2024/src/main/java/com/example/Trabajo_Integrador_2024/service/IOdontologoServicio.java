package com.example.Trabajo_Integrador_2024.service;

import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoServicio {
    Odontologo guardar (Odontologo odontologo);
    Odontologo buscarPorId(Long id) throws ResourceNotFoundException;
    void eliminar(Long id);
    Odontologo actualizar (Odontologo odontologo);

    List<Odontologo> listarTodos();

    Odontologo buscarPorMatricula(String matricula);
    Boolean existeMatricula(String matricula);

}
