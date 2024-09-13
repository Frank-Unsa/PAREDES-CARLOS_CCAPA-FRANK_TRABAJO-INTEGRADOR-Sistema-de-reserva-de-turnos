package com.example.Trabajo_Integrador_2024.service.impl;

import com.example.Trabajo_Integrador_2024.entity.Domicilio;
import com.example.Trabajo_Integrador_2024.entity.Paciente;
import com.example.Trabajo_Integrador_2024.exception.DuplicateResourceException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.IPacienteRepository;
import com.example.Trabajo_Integrador_2024.service.IPacienteServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServicioImplTest {

    @Autowired
    private IPacienteServicio pacienteServicio;
    @Autowired
    private IPacienteRepository iPacienteRepository;

    @BeforeEach
    void setUp() {
        // Limpia el repo de paciente antes de cada test
        iPacienteRepository.deleteAll();
    }

    @Test
    void guardar() {
        // Arrange
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 1");
        domicilio.setNumero(1234);
        domicilio.setLocalidad("Socabaya");
        domicilio.setProvincia("Arequipa");

        Paciente paciente1 = new Paciente();
        paciente1.setNombre("Fernando");
        paciente1.setApellido("Ccapa");
        paciente1.setDni("12345"); //<----
        paciente1.setFechaAlta(LocalDate.parse("2024-01-06"));
        paciente1.setDomicilio(domicilio);

        // Act
        Paciente pacienteGuardado = pacienteServicio.guardar(paciente1);

        // Assert
        Assertions.assertNotNull(pacienteGuardado);
        Assertions.assertEquals("Fernando", pacienteGuardado.getNombre());
        Assertions.assertEquals("Calle 1", pacienteGuardado.getDomicilio().getCalle());
    }

    @Test
    public void guardarPacienteDuplicadoTest() {
        // Arrange
        Domicilio domicilio1 = new Domicilio();
        domicilio1.setCalle("Calle 2");
        domicilio1.setNumero(12345);
        domicilio1.setLocalidad("Socabaya");
        domicilio1.setProvincia("Arequipa");

        Paciente paciente2 = new Paciente();
        paciente2.setNombre("Frank");
        paciente2.setApellido("Ccapa");
        paciente2.setDni("12212"); //<------- Mismo DNI
        paciente2.setFechaAlta(LocalDate.parse("2024-01-06"));
        paciente2.setDomicilio(domicilio1);

        pacienteServicio.guardar(paciente2);

        // Act
        Domicilio domicilio2 = new Domicilio();
        domicilio2.setCalle("Calle 2");
        domicilio2.setNumero(12234);
        domicilio2.setLocalidad("Miraflores");
        domicilio2.setProvincia("Lima");

        Paciente pacienteDuplicado = new Paciente();
        pacienteDuplicado.setNombre("Raul");
        pacienteDuplicado.setApellido("Ccapa");
        pacienteDuplicado.setDni("12212"); // <--- Mismo DNI
        pacienteDuplicado.setFechaAlta(LocalDate.parse("2024-01-07"));
        pacienteDuplicado.setDomicilio(domicilio2);

        // Assert
        Assertions.assertThrows(DuplicateResourceException.class, () -> {
            pacienteServicio.guardar(pacienteDuplicado);
        });
    }

    @Test
    void eliminarPacienteExistenteTest() throws ResourceNotFoundException {
        // Arrange
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 1");
        domicilio.setNumero(100);
        domicilio.setLocalidad("Socabaya");
        domicilio.setProvincia("Arequipa");

        Paciente paciente = new Paciente();
        paciente.setNombre("Frank");
        paciente.setApellido("Ccapa");
        paciente.setDni("123654");
        paciente.setFechaAlta(LocalDate.parse("2024-09-19"));
        paciente.setDomicilio(domicilio);

        // Guardamos el paciente
        Paciente pacienteGuardado = pacienteServicio.guardar(paciente);
        Long idPaciente = pacienteGuardado.getId();

        // Act
        pacienteServicio.eliminar(idPaciente);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pacienteServicio.buscarPorId(idPaciente);
        });
    }
}