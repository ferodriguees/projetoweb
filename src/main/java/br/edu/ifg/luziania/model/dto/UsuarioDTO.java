package br.edu.ifg.luziania.model.dto;

public class UsuarioDTO {

    private String nome;

    private String email;

    private String perfil;

    private String senha;

    private String cpf;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String nome, String email, String cpf, String perfil, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.perfil = perfil;
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
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
