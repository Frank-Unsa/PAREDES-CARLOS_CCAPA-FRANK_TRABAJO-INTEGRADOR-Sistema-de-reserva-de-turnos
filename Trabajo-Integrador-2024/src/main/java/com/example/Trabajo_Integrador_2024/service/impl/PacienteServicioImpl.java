package com.example.Trabajo_Integrador_2024.service.impl;
import com.example.Trabajo_Integrador_2024.entity.Paciente;
import com.example.Trabajo_Integrador_2024.exception.DuplicateResourceException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.IPacienteRepository;
import com.example.Trabajo_Integrador_2024.service.IPacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicioImpl implements IPacienteServicio {

    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Override
    public Paciente guardar(Paciente paciente) throws DuplicateResourceException {
        if (iPacienteRepository.existsByDni(paciente.getDni()))
            throw new DuplicateResourceException("El paciente con Dni:"+ paciente.getDni()+ " ya existe");
        else {
            return iPacienteRepository.save(paciente);
        }
    }

    @Override
    public Paciente buscarPorId(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = iPacienteRepository.findById(id);
        if(pacienteBuscado.isPresent())  {
            return pacienteBuscado.get();
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }

    }

    @Override
    public List<Paciente> listarTodos() {

        return iPacienteRepository.findAll();
    }
    @Override
    public Paciente actualizar(Paciente paciente) throws ResourceNotFoundException, DuplicateResourceException {
        Long idPaciente = paciente.getId();

        if (iPacienteRepository.existsById(idPaciente)) {
            Paciente pacienteExistente = iPacienteRepository.findByDni(paciente.getDni());
            if (pacienteExistente != null && !pacienteExistente.getId().equals(paciente.getId())) {
                throw new DuplicateResourceException("El DNI " + paciente.getDni() + " ya está registrado por otro paciente.");
            }
            return iPacienteRepository.save(paciente);
        } else {
            throw new ResourceNotFoundException("No se encontró el paciente con id: " + idPaciente);
        }
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscadoE = iPacienteRepository.findById(id);
        if (pacienteBuscadoE.isPresent()) {
            iPacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException ("No se encontro el paciente con id: " + id);
        }

    }
    @Override
    public Boolean existeDni(String dni) {
        return iPacienteRepository.existsByDni(dni);
    }
}
