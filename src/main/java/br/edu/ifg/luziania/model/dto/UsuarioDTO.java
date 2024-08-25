package br.edu.ifg.luziania.model.dto;

import jakarta.ws.rs.FormParam;

public class UsuarioDTO {
    @FormParam("nome")
    private String nome;
    @FormParam("email")
    private String email;
    @FormParam("senha")
    private String senha;
    @FormParam("cpf")
    private String cpf;

    public UsuarioDTO() {
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
