package com.example.Trabajo_Integrador_2024.service.impl;


import com.example.Trabajo_Integrador_2024.entity.Turno;
import com.example.Trabajo_Integrador_2024.exception.BadRequestException;
import com.example.Trabajo_Integrador_2024.exception.ConflictException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.IOdontologoRepository;
import com.example.Trabajo_Integrador_2024.repository.IPacienteRepository;
import com.example.Trabajo_Integrador_2024.repository.ITurnoRepository;
import com.example.Trabajo_Integrador_2024.service.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TurnoServicioImpl implements ITurnoServicio {

    @Autowired
    private ITurnoRepository iTurnoRepository;
    @Autowired
    private IPacienteRepository iPacienteRepository;
    @Autowired
    private IOdontologoRepository iOdontologoRepository;

    @Override
    public Turno guardar(Turno turno) throws BadRequestException, ConflictException {
        Long idPaciente = turno.getPaciente().getId();
        Long idOdontologo = turno.getOdontologo().getId();
        LocalDate fechaTurno = turno.getFecha();

        // Verificar existencia del paciente y odontólogo
        if (idPaciente == null || !iPacienteRepository.existsById(idPaciente) ) {
            throw new BadRequestException("El paciente con ID " + idPaciente + " no existe.");
        }
        if (idOdontologo == null || !iOdontologoRepository.existsById(idOdontologo)) {
            throw new BadRequestException("El odontólogo con ID " + idOdontologo + " no existe.");
        }
        if (fechaTurno == null) {
            throw new BadRequestException("La fecha del turno no puede estar vacía.");
        }
        // Verificamos si ya existe un turno en la misma fecha con el mismo paciente y otro odontólogo
        Turno conflictoPaciente = iTurnoRepository.findByPacienteAndFecha(idPaciente, fechaTurno);
        if (conflictoPaciente != null && !conflictoPaciente.getId().equals(turno.getId())) {
            throw new ConflictException("El paciente con ID " + idPaciente + " ya tiene un turno en la fecha " + fechaTurno);
        }
        // Verificamos si ya existe un turno en la misma fecha con el mismo odontólogo y otro paciente
        Turno conflictoOdontologo = iTurnoRepository.findByOdontologoAndFecha(idOdontologo, fechaTurno);
        if (conflictoOdontologo != null && !conflictoOdontologo.getId().equals(turno.getId())) {
            throw new ConflictException("El odontólogo con ID " + idOdontologo + " ya tiene un turno en la fecha " + fechaTurno );
        }

        return iTurnoRepository.save(turno);
    }
    @Override
    public Turno buscarPorId(Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado =  iTurnoRepository.findById(id);
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
        if (iTurnoRepository.existsById(id)){
            iTurnoRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("El turno con id no existe: " + id);
        }

    }
    @Override
    public Turno actualizar(Turno turno) throws ResourceNotFoundException, ConflictException {
        // Verifica si el turno existe antes de actualizar
        if (!iTurnoRepository.existsById(turno.getId())) {
            throw new ResourceNotFoundException("No se encontró el turno con id: " + turno.getId());
        }

        // Verificamos si ya existe un turno en la misma fecha con el mismo odontólogo y otro paciente
        Turno conflictoOdontologo = iTurnoRepository.findByOdontologoAndFecha(turno.getOdontologo().getId(), turno.getFecha());
        if (conflictoOdontologo != null && !conflictoOdontologo.getId().equals(turno.getId())) {
            throw new ConflictException("El odontólogo con ID " + turno.getOdontologo().getId() + " ya tiene un turno en la fecha " + turno.getFecha());
        }

        // Verificamos si ya existe un turno en la misma fecha con el mismo paciente y otro odontólogo
        Turno conflictoPaciente = iTurnoRepository.findByPacienteAndFecha(turno.getPaciente().getId(), turno.getFecha());
        if (conflictoPaciente != null && !conflictoPaciente.getId().equals(turno.getId())) {
            throw new ConflictException("El paciente con ID " + turno.getPaciente().getId() + " ya tiene un turno en la fecha " + turno.getFecha());
        }

        return iTurnoRepository.save(turno);
    }

}
