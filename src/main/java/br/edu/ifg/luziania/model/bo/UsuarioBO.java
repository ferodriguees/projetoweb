package br.edu.ifg.luziania.model.bo;

import br.edu.ifg.luziania.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import br.edu.ifg.luziania.model.security.jwt.GenerateToken;
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
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setSenha("c" + usuarioDTO.getCpf()); // Senha padrão: 'c' seguido do CPF
        usuario.setPerfil("ADMIN"); // Ou outro identificador para perfis de administrador

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
    public Usuario autenticarUsuario(String email, String senha) {
        Usuario usuario = usuarioDAO.find("email", email)
                .firstResultOptional()
                .filter(u -> u.getSenha().equals(senha))
                .orElse(null);

        return usuario; // Se o usuário for encontrado e a senha corresponder, retorna o usuário; senão retorna null
    }
    public String gerarTokenJWT(Usuario usuario) {
        return GenerateToken.generate(usuario);
    }

}

