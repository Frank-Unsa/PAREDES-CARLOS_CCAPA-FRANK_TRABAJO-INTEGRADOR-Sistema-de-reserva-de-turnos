package com.example.Trabajo_Integrador_2024.controller;
import com.example.Trabajo_Integrador_2024.entity.Paciente;
import com.example.Trabajo_Integrador_2024.service.IPacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteServicio iPacienteServicio;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(iPacienteServicio.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iPacienteServicio.guardar(paciente));
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos(){
        return ResponseEntity.ok(iPacienteServicio.listarTodos());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        iPacienteServicio.eliminar(id);
        //204 No Content: Si la eliminación es exitosa y no hay contenido que devolver en la respuesta.
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPacientePorId(@PathVariable Long id, @RequestBody Paciente paciente) {
        paciente.setId(id);
        this.iPacienteServicio.actualizar(paciente);
        return ResponseEntity.status(HttpStatus.OK).body(iPacienteServicio.actualizar(paciente));
    }
    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(iPacienteServicio.actualizar(paciente));
    }

}
