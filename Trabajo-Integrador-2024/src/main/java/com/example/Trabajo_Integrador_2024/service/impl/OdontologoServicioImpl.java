package com.example.Trabajo_Integrador_2024.service.impl;
import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.exception.DuplicateResourceException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.IOdontologoRepository;
import com.example.Trabajo_Integrador_2024.service.IOdontologoServicio;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoServicioImpl implements IOdontologoServicio {
    private static  final Logger LOGGER=Logger.getLogger(OdontologoServicioImpl.class);
    @Autowired
    private IOdontologoRepository iOdontologoRepository;

    @Override
    public Odontologo guardar(Odontologo odontologo) throws DuplicateResourceException {
        String matricula=odontologo.getMatricula();
        if(existeMatricula(odontologo.getMatricula()))
            throw new DuplicateResourceException("La matricula "+ matricula +" ya existe");
        else {
            LOGGER.info("Odontologo guardado: "+ odontologo.getNombre() + " "+ odontologo.getApellido());
            return iOdontologoRepository.save(odontologo);
        }

    }
    @Override
    public Odontologo buscarPorId(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = iOdontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            return odontologoBuscado.get();
        } else {
            throw new ResourceNotFoundException("No se encontro el odontologo con id : " + id);
        }
    }
    @Override
    public void eliminar(Long id) throws ResourceNotFoundException  {
        if(iOdontologoRepository.existsById(id)){
            LOGGER.info("Odontologo guardado con ID: " +id);
            iOdontologoRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("No se encontro el odontologo con id: "+id);
        }

    }
    public Odontologo actualizar(Odontologo odontologo) throws ResourceNotFoundException, DuplicateResourceException {
        if (iOdontologoRepository.existsById(odontologo.getId())) {

            Odontologo odontologoExistente = iOdontologoRepository.findByMatricula(odontologo.getMatricula());
            if (odontologoExistente != null && !odontologoExistente.getId().equals(odontologo.getId())) {
                throw new DuplicateResourceException("La matrícula " + odontologo.getMatricula() + " ya existe.");
            }
            return iOdontologoRepository.save(odontologo);
        } else {
            throw new ResourceNotFoundException("El odontólogo con id: " + odontologo.getId() + " no existe.");
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        return iOdontologoRepository.findAll();
    }

    @Override
    public Odontologo buscarPorMatricula(String matricula) throws ResourceNotFoundException {
        Odontologo odontologoBuscadoMatricula = iOdontologoRepository.findByMatricula(matricula);
        if (odontologoBuscadoMatricula != null) {
            return odontologoBuscadoMatricula;
        } else {
            throw new ResourceNotFoundException("No se encontro el odontologo con matricula : " + matricula);
        }
    }
    @Override
    public Boolean existeMatricula(String matricula) {
        return iOdontologoRepository.existsByMatricula(matricula);
    }
}
