package com.example.Trabajo_Integrador_2024.service.impl;

import com.example.Trabajo_Integrador_2024.entity.Domicilio;
import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.entity.Paciente;
import com.example.Trabajo_Integrador_2024.entity.Turno;
import com.example.Trabajo_Integrador_2024.exception.BadRequestException;
import com.example.Trabajo_Integrador_2024.exception.ConflictException;
import com.example.Trabajo_Integrador_2024.exception.ResourceNotFoundException;
import com.example.Trabajo_Integrador_2024.repository.ITurnoRepository;
import com.example.Trabajo_Integrador_2024.service.IOdontologoServicio;
import com.example.Trabajo_Integrador_2024.service.IPacienteServicio;
import com.example.Trabajo_Integrador_2024.service.ITurnoServicio;
import org.junit.jupiter.api.Assertions;
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

    private Paciente cargarDatosPrimerPaciente(int count){
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle 1");
        domicilio.setNumero(1234);
        domicilio.setLocalidad("Socabaya");
        domicilio.setProvincia("Arequipa");

        // Arrange
        Paciente paciente = new Paciente();
        paciente.setNombre("Frank");
        paciente.setApellido("Ccapa");
        paciente.setDni("12345678"+count);
        paciente.setFechaAlta(LocalDate.of(2024, 1, 1));
        paciente.setDomicilio(domicilio);
        return pacienteServicio.guardar(paciente);
    }
    private Odontologo cargarDatosPrimerOdontologo( int count){
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Raul");
        odontologo.setApellido("Ccapa");
        odontologo.setMatricula("A123"+count);
        return odontologoServicio.guardar(odontologo);
    }
    @Test
    void guardar() {
        // Arrange - Act

        Paciente paciente = cargarDatosPrimerPaciente(1);
;
        Odontologo odontologo = cargarDatosPrimerOdontologo(1);

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

        Odontologo odontologo = cargarDatosPrimerOdontologo(2);

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
        Paciente paciente = cargarDatosPrimerPaciente(2);
        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(new Odontologo()); // <-- Odontologo no guardado
        turno.setFecha(LocalDate.of(2024, 1, 15));

        // Act - Assert
        assertThrows(BadRequestException.class, () -> {
            turnoServicio.guardar(turno);
        });
    }
    @Test
    void eliminarTurnoExistenteTest() throws ResourceNotFoundException {
        // Arrange
        Paciente paciente1 = cargarDatosPrimerPaciente(3);
        Odontologo odontologo1 = cargarDatosPrimerOdontologo(3);

        Turno turno = new Turno();
        turno.setPaciente(paciente1);
        turno.setOdontologo(odontologo1);
        turno.setFecha(LocalDate.of(2024, 10, 10));

        Turno turnoGuardado = turnoServicio.guardar(turno);
        Long idTurno = turnoGuardado.getId();

        // Act
        turnoServicio.eliminar(idTurno);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            turnoServicio.buscarPorId(idTurno);
        });
    }
    @Test
    void eliminarTurnoNoExistenteTest() {
        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            turnoServicio.eliminar(111L); // ID inexistente ..
        });

        assertEquals("El turno con id no existe: 111", thrown.getMessage());
    }
    @Test
    void actualizarTurnoExistenteTest() throws ResourceNotFoundException, ConflictException {
        // Arrange
        Paciente paciente = cargarDatosPrimerPaciente(4);
        Odontologo odontologo = cargarDatosPrimerOdontologo(4);

        Turno turnoOriginal = new Turno();
        turnoOriginal.setPaciente(paciente);
        turnoOriginal.setOdontologo(odontologo);
        turnoOriginal.setFecha(LocalDate.of(2024, 10, 10));

        // Guardamos el turno original
        Turno turnoGuardado = turnoServicio.guardar(turnoOriginal);

        // Creamos una nueva instancia del turno con cambios
        Turno turnoActualizado = turnoGuardado;
        turnoActualizado.setFecha(LocalDate.of(2024, 11, 10));

        // Act
        Turno turnoModificado = turnoServicio.actualizar(turnoActualizado);

        // Assert
        Assertions.assertEquals(LocalDate.of(2024, 11, 10), turnoModificado.getFecha());
    }
    @Test
    void actualizarTurnoNoExistenteTest() {
        // Arrange
        Turno turnoInexistente = new Turno();
        turnoInexistente.setId(111L); // ID inexistente --

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            turnoServicio.actualizar(turnoInexistente);
        });

        assertEquals("No se encontró el turno con id: 111", thrown.getMessage());
    }

}