package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.Agendamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AgendamentoDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            entityManager.persist(agendamento);
        } else {
            entityManager.merge(agendamento);
        }
    }

    public List<Agendamento> listarTodos() {
        return entityManager.createQuery("SELECT a FROM Agendamento a", Agendamento.class).getResultList();
    }
}
