package br.edu.ifg.luziania.model.dao;

import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class UsuarioDAO implements PanacheRepository<Usuario> {
    @PersistenceContext
    private EntityManager entityManager;

    public Usuario buscarPorCpf(String cpf) {
        try {
            Usuario usuario = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf", Usuario.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();

            // Adiciona log para verificar se o usuário foi encontrado
            System.out.println("Usuário encontrado: " + usuario.getNome() + ", CPF: " + usuario.getCpf());
            return usuario;
        } catch (NoResultException e) {
            System.out.println("Nenhum usuário encontrado com o CPF: " + cpf);
            return null; // Retorna null se não encontrar nenhum resultado
        }
    }

    public void persist(Usuario usuario) {
        entityManager.merge(usuario);  // Atualiza o usuário no banco de dados
    }
}
