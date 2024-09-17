package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioBO {

    @Inject
    UsuarioDAO usuarioDAO;

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioDAO.findByIdOptional(id);
    }


    @Transactional
    public void cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha("c" + usuarioDTO.getCpf()); // Senha padrão: 'c' seguido do CPF
        usuario.setPerfil(usuarioDTO.getPerfil()); // Ou outro identificador para perfis de administrador

        usuarioDAO.persist(usuario);
    }

    @Transactional
    public void cadastrarAdmin(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha("c" + usuarioDTO.getCpf()); // Senha padrão: 'c' seguido do CPF
        usuario.setPerfil("admin"); // Ou outro identificador para perfis de administrador

        usuarioDAO.persist(usuario);
    }

    @Transactional
    public void atualizarUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioDAO.findByIdOptional(id);
        if (usuarioExistente.isPresent()) {
            Usuario u = usuarioExistente.get();
            u.setNome(usuario.getNome());
            u.setEmail(usuario.getEmail());
            u.setSenha(usuario.getSenha());
        }
    }

    @Transactional
    public void deletarUsuario(Long id) {
        usuarioDAO.deleteById(id);
    }

    @Transactional
    public Usuario autenticarUsuario(String username, String senha) {
        return usuarioDAO.find("username", username)
                .firstResultOptional()
                .filter(u -> u.getSenha().equals(senha))
                .orElse(null); // Retorna o usuário se encontrado e senha corresponder, senão retorna null
    }

    // Método para buscar o usuário pelo CPF
    public UsuarioDTO buscarUsuarioPorCpf(String cpf) {
        Usuario usuario = usuarioDAO.buscarPorCpf(cpf);
        if (usuario != null) {
            System.out.println("Dados do Usuário: Nome=" + usuario.getNome() + ", CPF=" + usuario.getCpf() + ", Email=" + usuario.getEmail());
            // Converte a entidade Usuario para UsuarioDTO
            return new UsuarioDTO(usuario.getNome(), usuario.getUsername(), usuario.getEmail(), usuario.getCpf(), usuario.getPerfil(), usuario.getSenha());
        }
        return null;
    }

    public UsuarioDTO buscarUsuarioPorUsername(String username) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null) {
            // Converte o objeto Usuario para UsuarioDTO
            return new UsuarioDTO(usuario.getNome(), usuario.getUsername(), usuario.getEmail(), usuario.getPerfil(), usuario.getCpf(), usuario.getSenha());
        }

        return null; // Retorna null se o usuário não for encontrado
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario != null) {
            System.out.println("Dados do Usuário: Nome=" + usuario.getNome() + ", CPF=" + usuario.getCpf() + ", Email=" + usuario.getEmail());
            // Converte a entidade Usuario para UsuarioDTO
            return new UsuarioDTO(usuario.getNome(), usuario.getUsername(), usuario.getEmail(), usuario.getCpf(), usuario.getPerfil(), usuario.getSenha());
        }
        return null;
    }

    // Método para atualizar o usuário
    public void atualizarUsuario(UsuarioDTO usuarioAtualizado) {
        Usuario usuarioEntidade = usuarioDAO.buscarPorCpf(usuarioAtualizado.getCpf());

        if (usuarioEntidade != null) {
            // Atualiza os dados na entidade
            usuarioEntidade.setNome(usuarioAtualizado.getNome());
            usuarioEntidade.setPerfil(usuarioAtualizado.getPerfil());

            // Atualiza a senha se estiver presente
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuarioEntidade.setSenha(usuarioAtualizado.getSenha());
            }

            // Atualiza a entidade no banco
            usuarioDAO.persist(usuarioEntidade);
        }
    }

    public List<Usuario> pesquisarUsuarios(String nome, String email, String username) {
        // Qualquer lógica de negócios, validações ou regras aplicadas antes da busca
        if ((nome == null || nome.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (username == null || username.isEmpty())) {
            // Se nenhum filtro for fornecido, talvez não permita a pesquisa
            throw new IllegalArgumentException("Pelo menos um critério de pesquisa deve ser fornecido.");
        }

        // Chama o DAO para realizar a consulta
        return usuarioDAO.pesquisarUsuarios(nome, email, username);
    }
}

