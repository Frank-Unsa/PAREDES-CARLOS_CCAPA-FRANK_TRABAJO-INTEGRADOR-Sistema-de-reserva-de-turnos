package com.example.Trabajo_Integrador_2024.service.impl;


import com.example.Trabajo_Integrador_2024.entity.Turno;
import com.example.Trabajo_Integrador_2024.exception.BadRequestException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.IOdontologoRepository;
import com.example.Trabajo_Integrador_2024.repository.IPacienteRepository;
import com.example.Trabajo_Integrador_2024.repository.ITurnoRepository;
import com.example.Trabajo_Integrador_2024.service.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoServicioImpl implements ITurnoServicio {

    @Autowired
    private ITurnoRepository iTurnoRepository;

    @Autowired
    private IPacienteRepository iPacienteRepository;
    @Autowired
    private IOdontologoRepository iOdontologoRepository;

    //    @Override
//    public Turno guardar(Turno turno) {
//        return iTurnoRepository.save(turno);
//    }
    @Override
    public Turno guardar(Turno turno) throws BadRequestException {
    // Validar si el paciente existe
        if (!iPacienteRepository.existsById(turno.getPaciente().getId()))
            throw new BadRequestException("El paciente con ID " + turno.getPaciente().getId() + " no existe.");

        // Validar si el odontólogo existe
        if (!iOdontologoRepository.existsById(turno.getOdontologo().getId())) {
            throw new BadRequestException("El odontólogo con ID " + turno.getOdontologo().getId() + " no existe.");
        }
        return iTurnoRepository.save(turno);
    }

    @Override
    public Turno buscarPorId(Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado =  iTurnoRepository.findById(id);
        //return turnoBuscado.orElse(null);
        if (turnoBuscado.isPresent()) {
            return turnoBuscado.get();
        } else {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);

        }
    }

    @Override
    public List<Turno> listarTodos() {
        return iTurnoRepository.findAll();
    }

    @Override
    public void eliminar(Long id) {
        iTurnoRepository.deleteById(id);
    }

    @Override
    public Turno actualizar(Turno turno) {
        // Verifica si el turno existe antes de actualizar
        if (iTurnoRepository.existsById(turno.getId())) {
            return iTurnoRepository.save(turno);
        } else {
            throw new RuntimeException("El odontólogo no existe");
        }
    }
}
