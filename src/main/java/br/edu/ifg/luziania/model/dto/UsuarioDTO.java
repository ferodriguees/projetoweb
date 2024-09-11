package br.edu.ifg.luziania.model.dto;

import jakarta.ws.rs.FormParam;

public class UsuarioDTO {

    private String nome;

    private String email;

    private String senha;

    private String cpf;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nome, String email, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }


    // Getters e Setters
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
