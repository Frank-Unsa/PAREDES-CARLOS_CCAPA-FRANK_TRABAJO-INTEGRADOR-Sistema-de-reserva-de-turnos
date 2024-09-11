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
        return ResponseEntity.ok(iPacienteServicio.guardar(paciente));
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos(){
        return ResponseEntity.ok(iPacienteServicio.listarTodos());
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> eliminarPorId(@RequestParam Long id){
//        return ResponseEntity.ok(iPacienteServicio.eliminar(id));
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id) {
        ResponseEntity response = null;
        if (iPacienteServicio.eliminar(id))
            response = ResponseEntity.status(HttpStatus.OK).build();
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPacientePorId(@PathVariable Long id, @RequestBody Paciente paciente) {
        paciente.setId(id);
        this.iPacienteServicio.actualizar(paciente);
        return ResponseEntity.noContent().build();
    }
    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(iPacienteServicio.actualizar(paciente));
    }



}
