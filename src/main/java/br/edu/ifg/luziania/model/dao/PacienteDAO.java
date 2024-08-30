package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PacienteDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void salvar(Paciente paciente) {
        if (paciente.getId() == null) {
            entityManager.persist(paciente);
        } else {
            entityManager.merge(paciente);
        }
    }

    public Paciente findById(Long id) {
        return entityManager.find(Paciente.class, id);
    }

    public List<Paciente> findAll() {
        return entityManager.createQuery("SELECT p FROM Paciente p", Paciente.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Paciente paciente = findById(id);
        if (paciente != null) {
            entityManager.remove(paciente);
        }
    }
}
