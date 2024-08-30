package br.edu.ifg.luziania.model.bo.GerenciamentoDeSenhas;

import br.edu.ifg.luziania.model.dao.PacienteDAO;
import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.LinkedList;
import java.util.Queue;

@ApplicationScoped
public class AtendimentoBO {
    @Inject
    PacienteDAO pacienteDAO;

    private final Queue<Paciente> filaPacientes = new LinkedList<>();

    public void realizarAtendimento(String nome, String cpf) {
        Paciente paciente = new Paciente(nome, cpf);
        paciente.setNome(nome);
        paciente.setCpf(cpf);
        pacienteDAO.salvar(paciente);

        filaPacientes.add(paciente);
    }


    public Paciente chamarPaciente() {
        return filaPacientes.poll();
    }

    public boolean isFilaVazia() {
        return filaPacientes.isEmpty();
    }
}

