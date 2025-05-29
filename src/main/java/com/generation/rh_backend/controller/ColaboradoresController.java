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

import com.generation.rh_backend.dto.ColaboradorCreateBodyDTO;
import com.generation.rh_backend.model.Colaboradores;
import com.generation.rh_backend.service.ColaboradoresService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/colaboradores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Colaboradores", description = "Gerenciamento dos Colaboradores")
public class ColaboradoresController {
    @Autowired
    private ColaboradoresService colaboradoresService;

    @Operation(summary = "Lista todos os colaboradores", description = "Recupera uma lista completa de todos os colaboradores cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de colaboradores recuperada com sucesso.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Colaboradores.class))))
    })
    @GetMapping
    public ResponseEntity<List<Colaboradores>> getAll() {
        List<Colaboradores> colaboradores = colaboradoresService.getAll();
        return ResponseEntity.ok(colaboradores);
    }

    @Operation(summary = "Busca um colaborador por ID", description = "Recupera os dados de um colaborador específico com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Colaboradores.class))),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrado para o ID fornecido.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Colaboradores> getById(@PathVariable Long id) {
        Colaboradores colaborador = colaboradoresService.getById(id);
        return ResponseEntity.ok(colaborador);
    }

    @Operation(summary = "Busca colaboradores por nome", description = "Recupera uma lista de colaboradores cujo nome contém o termo pesquisado (case-insensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de colaboradores encontrada com sucesso.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Colaboradores.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum colaborador encontrado com o nome fornecido.", content = @Content)
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Colaboradores>> getByNome(@PathVariable String nome) {
        List<Colaboradores> colaboradores = colaboradoresService.getByNome(nome);
        if (colaboradores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(colaboradores);
    }

    @Operation(summary = "Busca um colaborador por telefone", description = "Recupera os dados de um colaborador específico com base no seu telefone (case-insensitive e busca por conteúdo).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Colaboradores.class))),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrado para o telefone fornecido.", content = @Content)
    })
    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Colaboradores> getByTelefone(@PathVariable String telefone) {
        return colaboradoresService.getByTelefone(telefone).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Busca um colaborador por email", description = "Recupera os dados de um colaborador específico com base no seu email (case-insensitive e busca por conteúdo).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Colaboradores.class))),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrado para o email fornecido.", content = @Content)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<Colaboradores> getByEmail(@PathVariable String email) {
        return colaboradoresService.getByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo colaborador", description = "Cadastra um novo colaborador no sistema. Validações de campos obrigatórios e unicidade de email/telefone são aplicadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Colaborador criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Colaboradores.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados do colaborador (ex: campos obrigatórios, formato de email, email/telefone já existente).", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Colaboradores> post(@Valid @RequestBody ColaboradorCreateBodyDTO colaboradorCreateBodyDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(colaboradoresService.create(colaboradorCreateBodyDTO));
    }

    @Operation(summary = "Atualiza um colaborador existente", description = "Atualiza os dados de um colaborador já cadastrado, identificado pelo seu ID. Validações de campos e unicidade de email/telefone (para outros colaboradores) são aplicadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador atualizado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Colaboradores.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique os dados do colaborador ou se o email/telefone já está em uso por outro colaborador.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrado para atualização (ID não existe).", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Colaboradores> put(@PathVariable Long id,
            @Valid @RequestBody Colaboradores colaboradorPayload) {

        Colaboradores colaboradorAtualizado = colaboradoresService.update(id, colaboradorPayload);
        return ResponseEntity.status(HttpStatus.OK).body(colaboradorAtualizado);
    }

    @Operation(summary = "Deleta um colaborador por ID", description = "Remove um colaborador do sistema com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Colaborador deletado com sucesso. Nenhum conteúdo retornado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Colaborador não encontrado para o ID fornecido (não pode ser deletado).", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colaboradoresService.delete(id);
        return ResponseEntity.noContent().build();
    }

}