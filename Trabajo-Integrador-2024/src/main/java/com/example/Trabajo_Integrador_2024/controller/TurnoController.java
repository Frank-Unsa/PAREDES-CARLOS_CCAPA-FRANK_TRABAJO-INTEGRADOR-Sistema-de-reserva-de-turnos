package com.example.Trabajo_Integrador_2024.controller;
import com.example.Trabajo_Integrador_2024.entity.Turno;
import com.example.Trabajo_Integrador_2024.service.ITurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ITurnoServicio iTurnoServicio;

    @PostMapping
    public ResponseEntity<Turno> guardar(@RequestBody Turno turno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iTurnoServicio.guardar(turno));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(iTurnoServicio.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        iTurnoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<Turno>> listarTodos(){
        return ResponseEntity.ok(iTurnoServicio.listarTodos());
    }
    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno){
        return ResponseEntity.ok(iTurnoServicio.actualizar(turno));
    }


}
