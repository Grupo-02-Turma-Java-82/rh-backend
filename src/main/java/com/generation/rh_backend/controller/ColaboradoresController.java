package com.generation.rh_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.rh_backend.model.Colaboradores;
import com.generation.rh_backend.service.ColaboradoresService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/colaboradores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ColaboradoresController {

	@Autowired
	private ColaboradoresService colaboradoresService;

	@GetMapping
	public ResponseEntity<List<Colaboradores>> getAll() {
		List<Colaboradores> colaboradores = colaboradoresService.getAll();
		return ResponseEntity.ok(colaboradores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Colaboradores> getById(@PathVariable Long id) {
		Colaboradores colaborador = colaboradoresService.getById(id);
		return ResponseEntity.ok(colaborador);
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Colaboradores>> getByNome(@PathVariable String nome) {
		List<Colaboradores> colaboradores = colaboradoresService.getByNome(nome);
		if (colaboradores.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(colaboradores);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Colaboradores> getByEmail(@PathVariable String email) {
		return colaboradoresService.getByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/telefone/{telefone}")
	public ResponseEntity<Colaboradores> getByTelefone(@PathVariable String telefone) {
		return colaboradoresService.getByTelefone(telefone).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Colaboradores> post(@Valid @RequestBody Colaboradores colaborador) {
		return ResponseEntity.status(HttpStatus.CREATED).body(colaboradoresService.create(colaborador));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Colaboradores> put(@PathVariable Long id,
			@Valid @RequestBody Colaboradores colaboradorPayload) {

		Colaboradores colaboradorAtualizado = colaboradoresService.update(id, colaboradorPayload);
		return ResponseEntity.status(HttpStatus.OK).body(colaboradorAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		colaboradoresService.delete(id);
		return ResponseEntity.noContent().build();
	}
}