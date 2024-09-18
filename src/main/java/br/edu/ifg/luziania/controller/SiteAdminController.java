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
        String perfil = (jwt.getGroups() != null && !jwt.getGroups().isEmpty())
                ? jwt.getGroups().stream().findFirst().orElse("sem-perfil")
                : "sem-perfil";

        return siteAdmin.data("perfil", perfil);
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
            return Response.status(Response.Status.BAD_REQUEST).entity
                    ("Erro ao cadastrar usuário").build();
        }
    }

    // Método que atualizar os dados do usuario
    @PUT
    @RolesAllowed({"admin", "atendente", "medico"})
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response atualizarUsuario(UsuarioDTO usuarioAtualizado) {
        String username = jwt.getClaim("sub");

        UsuarioDTO usuarioExistente = usuarioBO.buscarUsuarioPorUsername(username);

        if (usuarioExistente != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());

            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {

                usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            }

            usuarioBO.atualizarUsuario(usuarioExistente);

            return Response.ok("Usuário Atualizado! " +
                    "Faça o login novamente para atualizar suas credenciais.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
        }
    }

    @GET
    //@RolesAllowed({"admin", "atendente"})
    @Path("/usuario_list")
    //@RolesAllowed("admin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance listUsers() {
        return usuarios.instance();
    }

    @GET
    @Path("/pesquisar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("nome") String nome,
                                @QueryParam("cpf") String cpf,
                                @QueryParam("email") String email) {
        try {
            List<Usuario> usuarios = usuarioBO.pesquisarUsuarios(nome, cpf, email);

            if (usuarios.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Nenhum usuário encontrado.")
                        .build();
            }

            return Response.ok(usuarios).build();

        } catch (IllegalArgumentException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
