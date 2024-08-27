package br.edu.ifg.luziania.model.bo;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.LinkedList;
import java.util.Queue;

@ApplicationScoped
public class FilaBO {

    private Queue<String> fila = new LinkedList<>();

    public FilaBO() {
        // Adicionando algumas senhas de exemplo para a fila
        fila.add("G001");
        fila.add("P002");
        fila.add("G003");
    }

    public String chamarProximaSenha() {
        return fila.poll(); // Remove e retorna a próxima senha na fila, ou null se estiver vazia
    }
}
