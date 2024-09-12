package com.example.Trabajo_Integrador_2024.service.impl;

import com.example.Trabajo_Integrador_2024.entity.Domicilio;
import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.entity.Paciente;
import com.example.Trabajo_Integrador_2024.entity.Turno;
import com.example.Trabajo_Integrador_2024.exception.BadRequestException;
import com.example.Trabajo_Integrador_2024.repository.ITurnoRepository;
import com.example.Trabajo_Integrador_2024.service.IOdontologoServicio;
import com.example.Trabajo_Integrador_2024.service.IPacienteServicio;
import com.example.Trabajo_Integrador_2024.service.ITurnoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TurnoServicioImplTest {
    @Autowired
    private ITurnoServicio turnoServicio;
    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private IPacienteServicio pacienteServicio;

    @Autowired
    private IOdontologoServicio odontologoServicio;

    @BeforeEach
    void setUp() {
        // Limpia el repo de turnos antes de cada test
        turnoRepository.deleteAll();
    }
    @Test
    void guardar() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 1");
        domicilio.setNumero(1234);
        domicilio.setLocalidad("Socabaya");
        domicilio.setProvincia("Arequipa");

        // Arrange
        Paciente paciente = new Paciente();
        paciente.setNombre("Frank");
        paciente.setApellido("Ccapa");
        paciente.setDni("12345678");
        paciente.setFechaAlta(LocalDate.of(2024, 1, 1));
        paciente.setDomicilio(domicilio);
        paciente = pacienteServicio.guardar(paciente);

        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Dr. Raul");
        odontologo.setApellido("Ccapa");
        odontologo.setMatricula("A123");
        odontologo = odontologoServicio.guardar(odontologo);

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(LocalDate.of(2024, 1, 15));


        // Act
        Turno savedTurno = turnoServicio.guardar(turno);

        // Assert
        assertNotNull(savedTurno);
        assertNotNull(savedTurno.getId());
    }
    @Test
    public void guardarTurnoPacienteNoExistenteTest() {
        // Arrange
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Dr. Kenyo");
        odontologo.setApellido("Ccapa");
        odontologo.setMatricula("A1234");
        odontologo = odontologoServicio.guardar(odontologo);

        Turno turno = new Turno();
        turno.setPaciente(new Paciente()); // Paciente no guardado
        turno.setOdontologo(odontologo);
        turno.setFecha(LocalDate.of(2024, 1, 15));

        // Act - Assert
        assertThrows(BadRequestException.class, () -> {
            turnoServicio.guardar(turno);
        });
    }
    @Test
    public void guardarTurnoOdontologoNoExistenteTest() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setNombre("Fernando");
        paciente.setApellido("Ccapa");
        paciente.setDni("12345679");
        paciente.setFechaAlta(LocalDate.of(2024, 1, 1));
        paciente = pacienteServicio.guardar(paciente);

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(new Odontologo()); // <-- Odontologo no guardado
        turno.setFecha(LocalDate.of(2024, 1, 15));

        // Act - Assert
        assertThrows(BadRequestException.class, () -> {
            turnoServicio.guardar(turno);
        });
    }
}