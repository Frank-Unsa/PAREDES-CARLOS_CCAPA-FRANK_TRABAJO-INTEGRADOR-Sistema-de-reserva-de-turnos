package com.example.Trabajo_Integrador_2024.service.impl;

import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.exception.DuplicateResourceException;
import com.example.Trabajo_Integrador_2024.repository.IOdontologoRepository;
import com.example.Trabajo_Integrador_2024.service.IOdontologoServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OdontologoServicioImplTest {

    @Autowired
    private IOdontologoServicio odontologoServicio;

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @BeforeEach
    void setUp() {
        // Limpia el repo de odontologo antes de cada test
        odontologoRepository.deleteAll();
    }
    @Test
    void guardar()  {

        // Arrange

        // Dado un odontólogo con datos válidos
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Frank");
        odontologo.setApellido("Ccapa");
        odontologo.setMatricula("12345");

        // Act

        Odontologo odontologoGuardado = odontologoServicio.guardar(odontologo);

        // Assert

        Assertions.assertNotNull(odontologoGuardado.getId());
        Assertions.assertEquals("Frank", odontologoGuardado.getNombre());
        Assertions.assertEquals("12345", odontologoGuardado.getMatricula());


    }
    @Test
    public void GuardarOdontologoDuplicadoTest() {
        // Arrange
        Odontologo odontologo1 = new Odontologo();
        odontologo1.setNombre("Fernando");
        odontologo1.setApellido("Ccapa");
        odontologo1.setMatricula("12345"); //<----
        odontologoServicio.guardar(odontologo1);

        // Act
        Odontologo odontologoDuplicado = new Odontologo();
        odontologoDuplicado.setNombre("Raul");
        odontologoDuplicado.setApellido("Ccapa");
        odontologoDuplicado.setMatricula("12345"); //<--

        // Assert
        //Especifica el tipo de excepción que se espera que sea lanzada cuando se ejecuta el bloque
        // de codigo dentro del lambda
        Assertions.assertThrows(DuplicateResourceException.class, () -> {
            odontologoServicio.guardar(odontologoDuplicado);
        });

    }
}