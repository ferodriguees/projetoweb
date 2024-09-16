package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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


    public Paciente buscarPorCpf(String cpf) {
        try {
            return entityManager.createQuery("SELECT p FROM Paciente p WHERE p.cpf = :cpf", Paciente.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Paciente> findAll() {
        return entityManager.createQuery("SELECT p FROM Paciente p", Paciente.class).getResultList();
    }

//    @Transactional
//    public void delete(Long id) {
//        Paciente paciente = buscarPorCpf());
//        if (paciente != null) {
//            entityManager.remove(paciente);
//        }
//    }

    // Método para atualizar o status do paciente
//    public void atualizar(Paciente paciente) {
//        // O método merge() atualiza a entidade no banco de dados
//        pacienteDAO.salvar(paciente);
//    }
}
