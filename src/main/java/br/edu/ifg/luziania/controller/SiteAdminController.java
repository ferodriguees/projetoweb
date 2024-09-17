package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;


import java.util.List;

@Path("/site_admin")
public class SiteAdminController {

    @Inject
    Template siteAdmin;

    @Inject
    Template conta;
    @Inject
    Template usuarios;
    @Inject
    Template cadastroUsuario;

   @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioBO usuarioBO;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance carregarSiteAdmin() {
        // Verifica se o token tem grupos (perfis)
        String perfil = (jwt.getGroups() != null && !jwt.getGroups().isEmpty())
                ? jwt.getGroups().stream().findFirst().orElse("sem-perfil")
                : "sem-perfil";

        // Exemplo de como pegar outro claim, como o nome do usuário
        String nomeUsuario = jwt.getClaim("nome");

        return siteAdmin.data("perfil", perfil);  // Passa o perfil para o template
    }

    @GET
    @Path("/conta")
    public TemplateInstance getContaPage() {
        return conta.instance();
    }

    @GET
    @Path("/cadastrarUsuarioPage")
    public TemplateInstance getCadastroUser() {
        return cadastroUsuario.instance();
    }

    @POST
    @Path("/cadastrarUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioDTO usuarioDTO) {
        try {
            usuarioBO.cadastrarUsuario(usuarioDTO);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar usuário").build();
        }
    }

    // Método para atualizar os dados do usuário
    @PUT
    @RolesAllowed("admin")
    @Path("/atualizar/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response atualizarUsuario(@PathParam("cpf") String cpf, UsuarioDTO usuarioAtualizado) {
        UsuarioDTO usuarioExistente = usuarioBO.buscarUsuarioPorCpf(cpf);

        if (usuarioExistente != null) {
            // Atualiza o nome e o email do usuário
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());

            // Verifica se o campo senha foi preenchido
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                // Atualiza a senha, se foi fornecida
                usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            }

            // Chama o BO para atualizar o usuário
            usuarioBO.atualizarUsuario(usuarioExistente);

            return Response.ok("Usuário atualizado com sucesso.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
        }
    }

    @GET
    @Path("/usuario_list")
    //@RolesAllowed("admin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listUsers() {
        // Aqui você retorna o HTML da listagem e formulário de pesquisa
        return usuarios.instance();
    }

    @GET
    @Path("/pesquisar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("nome") String nome, @QueryParam("cpf") String cpf, @QueryParam("email") String email) {
        List<Usuario> usuarios = usuarioBO.pesquisarUsuarios(nome, cpf, email); // Busca os usuários no banco
        return Response.ok(usuarios).build();
    }
}
