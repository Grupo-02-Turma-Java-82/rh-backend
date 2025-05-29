package com.generation.rh_backend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_colaboradores")
public class Colaboradores {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID do Colaborador", accessMode = Schema.AccessMode.READ_ONLY)
	private Long Id;

	@NotBlank(message = "O Atributo Nome é Obrigatório!")
	@Schema(description = "Nome completo do colaborador. É obrigatório.", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
	private String nome;

	@NotBlank(message = "O Atributo Email é Obrigatório!")
	@Email(message = "O Atributo deve ser um email válido!")
	@Schema(description = "Endereço de e-mail do colaborador. Deve ser um email válido e é obrigatório.", example = "joao.silva@example.com", requiredMode = Schema.RequiredMode.REQUIRED, format = "email")
	private String email;

	@NotBlank(message = "O Atributo Telefone é Obrigatório!")
	@Schema(description = "Contato telefônico do colaborador. É obrigatório.", example = "+55 (19) 98743-7649", requiredMode = Schema.RequiredMode.REQUIRED)
	private String telefone;

	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	@Schema(description = "URL para a foto do colaborador. O link não pode exceder 5000 caracteres.", example = "https://example.com/foto_joao.jpg", maxLength = 5000)
	private String foto;

	@Size(max = 5000, message = "O link do curriculo não pode ser maior do que 5000 caracteres")
	@Schema(description = "URL para o currículo do colaborador. O link não pode exceder 5000 caracteres.", example = "https://example.com/curriculo_joao.pdf", maxLength = 5000)
	private String linkCurriculo;

    public void setId(Long id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLinkCurriculo() {
        return linkCurriculo;
    }

    public void setLinkCurriculo(String linkCurriculo) {
        this.linkCurriculo = linkCurriculo;
    }
}