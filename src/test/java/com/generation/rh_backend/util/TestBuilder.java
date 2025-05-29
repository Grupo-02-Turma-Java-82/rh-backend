package com.generation.rh_backend.util;

import com.generation.rh_backend.model.Colaboradores;

public class TestBuilder {

    public static Colaboradores criarUsuario(Long id, String nome, String email, String senha) {
    	Colaboradores usuario = new Colaboradores();
        usuario.setId(id);
    }
    
    public static UsuarioLogin criarUsuarioLogin(Long id, String nome, String email, String senha) {
        UsuarioLogin usuario = new UsuarioLogin();
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setUsuario(email);
        usuario.setSenha(senha);
        usuario.setFoto("-");
        return usuario;
    }

    public static Usuario criarUsuarioRoot() {
        return criarUsuario(null, "Root", "root@root.com", "rootroot");
    }
