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
    private Paciente cargarDatosPrimerPaciente(){
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
        return  pacienteServicio.guardar(paciente1);

    }
    private void cargarDatosSegundoPaciente(){
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
        pacienteServicio.guardar(paciente1);
    }
    private void pacienteNoExistente(){
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 1");
        domicilio.setNumero(999);
        domicilio.setLocalidad("Soca");
        domicilio.setProvincia("Areq");

        Paciente pacienteNoExistente = new Paciente();
        pacienteNoExistente.setId(111L); // Un ID que no existe
        pacienteNoExistente.setNombre("Fer");
        pacienteNoExistente.setApellido("Nando");
        pacienteNoExistente.setDni("87654321");
        pacienteNoExistente.setFechaAlta(LocalDate.parse("2024-01-01"));
        pacienteNoExistente.setDomicilio(domicilio);
        pacienteServicio.actualizar(pacienteNoExistente);
    }
    @Test
    void guardar() {
        // Arrange - Act
        Paciente pacienteGuardado = cargarDatosPrimerPaciente();

        // Assert
        Assertions.assertNotNull(pacienteGuardado);
        Assertions.assertEquals("Fernando", pacienteGuardado.getNombre());
        Assertions.assertEquals("Calle 1", pacienteGuardado.getDomicilio().getCalle());
    }

    @Test
    public void guardarPacienteDuplicadoTest() {

        // Arrange
        cargarDatosSegundoPaciente(); //Dni -- "12345"

        // Act - Assert
        Assertions.assertThrows(DuplicateResourceException.class, this::cargarDatosPrimerPaciente); //Dni -- "12345"

    }

    @Test
    void eliminarPacienteExistenteTest() throws ResourceNotFoundException {

        // Arrange
        Paciente pacienteGuardado = cargarDatosPrimerPaciente();
        Long idPaciente = pacienteGuardado.getId();

        // Act
        pacienteServicio.eliminar(idPaciente);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            pacienteServicio.buscarPorId(idPaciente);
        });
    }
    @Test
    void actualizarPacienteExistenteTest() throws ResourceNotFoundException, DuplicateResourceException {
        // Arrange
        Paciente pacienteGuardado =cargarDatosPrimerPaciente(); // DNI : 12345

        // Modificamos algunos datos del paciente
        pacienteGuardado.setNombre("Fernando");
        pacienteGuardado.setApellido("Usca");

        // Act
        Paciente pacienteActualizado = pacienteServicio.actualizar(pacienteGuardado);

        // Assert
        assertNotNull(pacienteActualizado);
        assertEquals("Fernando", pacienteActualizado.getNombre());
        assertEquals("Usca", pacienteActualizado.getApellido());
        assertEquals("12345", pacienteActualizado.getDni());
    }
    @Test
    void actualizarPacienteNoExistenteTest() {

        // Arrange - Act - Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, this::pacienteNoExistente); // ID 111L

        assertEquals("No se encontró el paciente con id: 111", thrown.getMessage());
    }
}