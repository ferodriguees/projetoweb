package br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas;

public class Senha {
    private String tipo;
    private int numero;

    public Senha(String tipo, int numero) {
        this.tipo = tipo;
        this.numero = numero;
    }


    public String getSenhaCompleta() {
        return tipo + String.format("%03d", numero);
    }

    // Getters e Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

}
