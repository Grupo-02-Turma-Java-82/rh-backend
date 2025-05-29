package com.generation.rh_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.rh_backend.dto.ColaboradorCreateBodyDTO;
import com.generation.rh_backend.model.Colaboradores;
import com.generation.rh_backend.repository.ColaboradoresRepository;

@Service
public class ColaboradoresService {

  @Autowired
  private ColaboradoresRepository colaboradoresRepository;

  public List<Colaboradores> getAll() {
    return colaboradoresRepository.findAll();
  }

  public Colaboradores getById(Long id) {
    return colaboradoresRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Colaborador não encontrado com o ID: " + id));
  }

  public List<Colaboradores> getByNome(String nome) {
    return colaboradoresRepository.findAllByNomeContainingIgnoreCase(nome);
  }

  public Optional<Colaboradores> getByEmail(String email) {
    return colaboradoresRepository.findAllByEmailContainingIgnoreCase(email);
  }

  public Optional<Colaboradores> getByTelefone(String telefone) {
    return colaboradoresRepository.findAllByTelefoneContainingIgnoreCase(telefone);
  }


  public Colaboradores create(ColaboradorCreateBodyDTO colaboradorDTO) {

    Colaboradores novoColaborador = new Colaboradores();

    novoColaborador.setNome(colaboradorDTO.getNome());
    novoColaborador.setEmail(colaboradorDTO.getEmail());
    novoColaborador.setTelefone(colaboradorDTO.getTelefone());
    novoColaborador.setFoto(colaboradorDTO.getFoto());
    novoColaborador.setLinkCurriculo(colaboradorDTO.getLinkCurriculo());

    if (novoColaborador.getEmail() != null) {
      colaboradoresRepository.findAllByEmailContainingIgnoreCase(novoColaborador.getEmail())
          .ifPresent(colaboradorExistente -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "O email '" + novoColaborador.getEmail() + "' já está cadastrado.");
          });
    }

    if (novoColaborador.getTelefone() != null) {
      colaboradoresRepository.findAllByTelefoneContainingIgnoreCase(novoColaborador.getTelefone())
          .ifPresent(colaboradorExistente -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "O telefone '" + novoColaborador.getTelefone() + "' já está cadastrado.");
          });
    }

    return colaboradoresRepository.save(novoColaborador);
  }

  public Colaboradores update(Long id, Colaboradores colaboradoresPayload) {
    Colaboradores colaboradorParaAtualizar = this.colaboradoresRepository.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado para atualização!"));

    if (colaboradoresPayload.getNome() != null) {
      colaboradorParaAtualizar.setNome(colaboradoresPayload.getNome());
    }

    if (colaboradoresPayload.getEmail() != null &&
        !colaboradoresPayload.getEmail().equals(colaboradorParaAtualizar.getEmail())) {

      this.colaboradoresRepository.findAllByEmailContainingIgnoreCase(colaboradoresPayload.getEmail())
          .ifPresent(colaboradorExistenteComEmail -> {
            if (!colaboradorExistenteComEmail.getId().equals(colaboradorParaAtualizar.getId())) {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                  "O email " + colaboradoresPayload.getEmail() + " já está em uso por outro colaborador.");
            }
          });
      colaboradorParaAtualizar.setEmail(colaboradoresPayload.getEmail());
    }

    if (colaboradoresPayload.getTelefone() != null &&
        !colaboradoresPayload.getTelefone().equals(colaboradorParaAtualizar.getTelefone())) {

      this.colaboradoresRepository.findAllByTelefoneContainingIgnoreCase(colaboradoresPayload.getTelefone())
          .ifPresent(colaboradorExistenteComTelefone -> {
            if (!colaboradorExistenteComTelefone.getId().equals(colaboradorParaAtualizar.getId())) {
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                  "O telefone " + colaboradoresPayload.getTelefone() + " já está em uso por outro colaborador.");
            }
          });
      colaboradorParaAtualizar.setTelefone(colaboradoresPayload.getTelefone());
    }

    return colaboradoresRepository.save(colaboradorParaAtualizar);
  }

  public void delete(Long id) {

    if (!colaboradoresRepository.existsById(id))
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Colaborador não encontrado!");

    colaboradoresRepository.deleteById(id);
  }

}
