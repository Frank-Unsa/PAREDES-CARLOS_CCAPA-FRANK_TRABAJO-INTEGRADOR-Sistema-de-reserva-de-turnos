package com.example.Trabajo_Integrador_2024.controller;

import com.example.Trabajo_Integrador_2024.entity.Odontologo;
import com.example.Trabajo_Integrador_2024.service.IOdontologoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private IOdontologoServicio odontologoServicio;
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(odontologoServicio.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Odontologo> guardar(@RequestBody Odontologo odontologo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoServicio.guardar(odontologo));
    }

    //listar todos
    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos() {
        return ResponseEntity.ok(odontologoServicio.listarTodos());
    }

    @GetMapping("matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(odontologoServicio.buscarPorMatricula(matricula));
    }
    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoServicio.actualizar(odontologo));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Odontologo> actualizarodontologoPorID(@PathVariable Long id, @RequestBody Odontologo odontologo) {
        odontologo.setId(id);
        this.odontologoServicio.actualizar(odontologo);
        return ResponseEntity.status(HttpStatus.OK).body(odontologoServicio.actualizar(odontologo));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        odontologoServicio.eliminar(id);
        //204 No Content: Si la eliminación es exitosa y no hay contenido que devolver en la respuesta.
        return ResponseEntity.noContent().build();
    }
}
