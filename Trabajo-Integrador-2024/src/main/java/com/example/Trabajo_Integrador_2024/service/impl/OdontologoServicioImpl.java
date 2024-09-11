package com.example.Trabajo_Integrador_2024.service.impl;


import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.exception.DuplicateResourceException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.IOdontologoRepository;
import com.example.Trabajo_Integrador_2024.service.IOdontologoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoServicioImpl implements IOdontologoServicio {

    @Autowired
    private IOdontologoRepository iOdontologoRepository;


    @Override
    public Odontologo guardar(Odontologo odontologo) throws DuplicateResourceException {
        String matricula=odontologo.getMatricula();
        if(existeMatricula(odontologo.getMatricula()))
            throw new DuplicateResourceException("La matricula "+ matricula +" ya existe");
        else {
            // logger (odontologo guardado: )
            return iOdontologoRepository.save(odontologo);
        }

    }

    @Override
    public Odontologo buscarPorId(Long id) throws ResourceNotFoundException {
        //va a buscar al odontologo y lo va a guardar en odontologoBuscado
        //o va a guardar un null en el odontologoBuscado
        Optional<Odontologo> odontologoBuscado = iOdontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            return odontologoBuscado.get();
        } else {
            //aca vamos a lanzar las excepciones
            //return null;
            throw new ResourceNotFoundException("No se encontro el odontologo con id : " + id);
        }
    }


    @Override
    public void eliminar(Long id) {
        iOdontologoRepository.deleteById(id);
    }

//    @Override
//    public void actualizar(Odontologo odontologo) {
//
//        if (odontologo.getId() != null) {
//            this.iOdontologoRepository.save(odontologo);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No permitido");
//        }
//
//    }
    @Override
    public Odontologo actualizar(Odontologo odontologo) {
        // Verifica si el odontólogo existe antes de actualizar
        if (iOdontologoRepository.existsById(odontologo.getId())) {
            return iOdontologoRepository.save(odontologo);
        } else {
            throw new RuntimeException("El odontólogo no existe");
        }
    }

    @Override
    public List<Odontologo> listarTodos() {
        return iOdontologoRepository.findAll();
    }

    @Override
    public Odontologo buscarPorMatricula(String matricula) {
        return iOdontologoRepository.findByMatricula(matricula);
    }
    @Override
    public Boolean existeMatricula(String matricula) {
        return iOdontologoRepository.existsByMatricula(matricula);
    }




}
