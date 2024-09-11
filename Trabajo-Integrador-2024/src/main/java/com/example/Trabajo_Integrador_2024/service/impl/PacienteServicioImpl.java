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
        if (existeDni(paciente.getDni()))
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

//    @Override
//    public void actualizar(Paciente paciente) {
//        iPacienteRepository.save(paciente);
//    }
    @Override
    public Paciente actualizar(Paciente paciente) {
        // Verifica si el odontólogo existe antes de actualizar
        if (iPacienteRepository.existsById(paciente.getId())) {
            return iPacienteRepository.save(paciente);
        } else {
            throw new RuntimeException("El paciente no existe");//crear excepcion
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        Optional<Paciente> pacienteBuscadoE = iPacienteRepository.findById(id);
        if (pacienteBuscadoE.isPresent()) {
            iPacienteRepository.deleteById(id);
            return true; // Se eliminó el paciente
        } else {
            return false; // No se encontró el paciente
        }

    }
    @Override
    public Boolean existeDni(String dni) {
        return iPacienteRepository.existsByDni(dni);
    }
}
