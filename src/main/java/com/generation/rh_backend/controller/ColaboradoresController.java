package com.generation.rh_backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.generation.rh_backend.model.Colaboradores;
import com.generation.rh_backend.repository.ColaboradoresRepository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/colaboradores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ColaboradoresController {

    @Autowired
    private ColaboradoresRepository colaboradoresRepository;

    // lsitar todos os colaboradores
    @GetMapping
    public ResponseEntity<List<Colaboradores>> getAll() {
        return ResponseEntity.ok(colaboradoresRepository.findAll());
    }

    // buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Colaboradores> getById(@PathVariable Long id) {
        return colaboradoresRepository.findById(id)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    // criar novo colaborador
    @PostMapping
    public ResponseEntity<Colaboradores> post(@Valid @RequestBody Colaboradores colaborador) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(colaboradoresRepository.save(colaborador));
    }

    // colaborador existente
    @PutMapping
    public ResponseEntity<Colaboradores> put(@Valid @RequestBody Colaboradores colaborador) {
        if (colaborador.getId() == null || !colaboradoresRepository.existsById(colaborador.getId()))
            return ResponseEntity.notFound().build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(colaboradoresRepository.save(colaborador));
    }
}
