package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/site_admin")
public class SiteAdminController {

    @Inject
    Template siteAdmin;

    @Inject
    Template conta;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UsuarioBO usuarioBO;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getSiteAdmin() {
        // Obtém o nome do usuário logado a partir do token JWT
        String emailUsuario = securityIdentity.getPrincipal().getName();

        // Busca o usuário no banco de dados usando o email (ou outro identificador)
        UsuarioDTO usuarioDTO = usuarioBO.buscarUsuarioPorEmail(emailUsuario);

        // Verifica se o usuário foi encontrado
        if (usuarioDTO != null) {
            String perfil = usuarioDTO.getPerfil();  // Obtém o perfil do usuário logado

            // Passa o nome do usuário e o perfil para o template
            return siteAdmin
                    .data("nomeUsuario", usuarioDTO.getNome())
                    .data("perfilUsuario", perfil);
                    //.data("barraNav", barraNav.instance());  // Envia o perfil para o template

        }

        // Caso o usuário não seja encontrado, redireciona para uma página de erro
        return siteAdmin.data("mensagemErro", "Usuário não encontrado");
    }

    @GET
    @Path("/conta")
    public TemplateInstance getContaPage() {
        return conta.instance();
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

    @Inject
    Template usuarios;

    @GET
    @Path("/usuario_list")
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
