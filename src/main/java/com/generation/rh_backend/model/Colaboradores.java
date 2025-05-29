package com.generation.rh_backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private Long Id;

    @NotBlank(message = "O Atributo Nome é Obrigatório!")
    private String nome;

    @NotBlank(message = "O Atributo Email é Obrigatório!")
    @Email(message = "O Atributo deve ser um email válido!")
    private String email;

    @NotBlank(message = "O Atributo Telefone é Obrigatório!")
    private String telefone;

    @Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
    private String foto;
    
    @Size(max = 5000, message = "O link do curriculo não pode ser maior do que 5000 caracteres")
    private String linkCurriculo;

    public Long getId() {
        return Id;
    }

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