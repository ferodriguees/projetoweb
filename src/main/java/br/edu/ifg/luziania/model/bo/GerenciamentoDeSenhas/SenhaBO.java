package br.edu.ifg.luziania.model.bo.GerenciamentoDeSenhas;


import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Senha;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.Queue;

@ApplicationScoped
public class SenhaBO {

    private Queue<Senha> filaSenhas = new LinkedList<>();
    private int contadorGeral = 0;
    private int contadorPreferencial = 0;

    public Senha gerarSenha(String tipo) {
        int numero;
        if (tipo.equalsIgnoreCase("G")) {
            numero = ++contadorGeral;
        } else if (tipo.equalsIgnoreCase("P")) {
            numero = ++contadorPreferencial;
        } else {
            throw new IllegalArgumentException("Tipo de fila inválido");
        }

        Senha novaSenha = new Senha(tipo, numero);
        filaSenhas.add(novaSenha);
        return novaSenha;
    }

    public Senha chamarProximaSenha() {
        return filaSenhas.poll();
    }
}
