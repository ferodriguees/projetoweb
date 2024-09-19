package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import br.edu.ifg.luziania.model.log.LogService;
import br.edu.ifg.luziania.model.log.LogType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioBO {

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    LogService logService;

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioDAO.findByIdOptional(id);
    }

    @Transactional
    public List<Usuario> findAll() {
        return usuarioDAO.listAll();
    }

    @Transactional
    public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha("c" + usuarioDTO.getCpf());
        usuario.setPerfil(usuarioDTO.getPerfil());

        usuarioDAO.persist(usuario);

        return usuario;
    }

    @Transactional
    public void cadastrarAdmin(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha("admin123");
        usuario.setPerfil("admin"); //

        usuarioDAO.persist(usuario);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioDAO.findById(id);
        if (usuario != null) {
            usuarioDAO.deleteById(id);

            logService.registerLog(usuario.getId(), LogType.USER_DELETION, "Usuário deletado: " + usuario.getUsername());
        }
    }

    @Transactional
    public Usuario autenticarUsuario(String username, String senha) {
        return usuarioDAO.find("username", username)
                .firstResultOptional()
                .filter(u -> u.getSenha().equals(senha))
                .orElse(null);
    }

    public UsuarioDTO buscarUsuarioPorUsername(String username) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null) {

            return new UsuarioDTO(usuario.getNome(), usuario.getUsername(), usuario.getEmail(), usuario.getPerfil(), usuario.getCpf(), usuario.getSenha());
        }

        return null;
    }

    public void atualizarUsuario(UsuarioDTO usuarioAtualizado) {
        Usuario usuarioEntidade = usuarioDAO.buscarPorUsername(usuarioAtualizado.getUsername());

        if (usuarioEntidade != null) {

            usuarioEntidade.setNome(usuarioAtualizado.getNome());
            usuarioEntidade.setPerfil(usuarioAtualizado.getPerfil());

            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuarioEntidade.setSenha(usuarioAtualizado.getSenha());
            }

            usuarioDAO.persist(usuarioEntidade);
        }
    }

    public List<Usuario> pesquisarUsuarios(String nome, String email, String username) {
        if ((nome == null || nome.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (username == null || username.isEmpty())) {
            throw new IllegalArgumentException("Pelo menos um critério de pesquisa deve ser fornecido.");
        }

        return usuarioDAO.pesquisarUsuarios(nome, email, username);
    }
}

