package com.generation.rh_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.rh_backend.model.Colaboradores;

public interface ColaboradoresRepository extends JpaRepository<Colaboradores, Long> {

  List<Colaboradores> findAllByNomeContainingIgnoreCase(String nome);

  Optional<Colaboradores> findAllByEmailContainingIgnoreCase(String email);

  Optional<Colaboradores> findAllByTelefoneContainingIgnoreCase(String telefone);

}