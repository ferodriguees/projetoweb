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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("/usuario")
public class UsuarioController {

    @Inject
    UsuarioBO usuarioBO;
    @Inject
    Template cadastro;

    @GET
    @Path("/cadastro")
    public TemplateInstance getCadastroPage() {
        return cadastro.instance();
    }

    @Inject
    Template conta;

    @GET
    @Path("/conta")
    public TemplateInstance getContaPage() {
        return conta.instance();
    }

    @GET
    public List<Usuario> listarUsuarios() {
        return usuarioBO.listarUsuarios();
    }

    @GET
    @Path("/{id}")
    public Response buscarUsuarioPorId(@PathParam("id") Long id) {
        Optional<Usuario> usuario = usuarioBO.buscarUsuarioPorId(id);
        return usuario.isPresent() ? Response.ok(usuario.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioDTO usuarioDTO) {
        // Chama o método do BO para cadastrar o usuário
        usuarioBO.cadastrarUsuario(usuarioDTO);

        // Redireciona para a página de login após o cadastro bem-sucedido
        return Response.ok("Usuário cadastrado com sucesso").build();
        //return Response.seeOther(URI.create("/login")).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarUsuario(@PathParam("id") Long id, Usuario usuario) {
        usuarioBO.atualizarUsuario(id, usuario);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarUsuario(@PathParam("id") Long id) {
        usuarioBO.deletarUsuario(id);
        return Response.noContent().build();
    }

    @Context
    SecurityContext securityContext;

    //recepcao do usuario, implementar a qualquer momento
    @GET
    @Path("/me")
    public String getUsuario() {
        JsonWebToken jwt = (JsonWebToken) securityContext.getUserPrincipal();
        String nome = jwt.getClaim("nome");
        return "Bem-vindo, " + nome;
    }

    // Método para buscar o usuário pelo CPF
    @GET
    @Path("/buscar/{cpf}")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterUsuarioPorCpf(@PathParam("cpf") String cpf) {
        UsuarioDTO usuario = usuarioBO.buscarUsuarioPorCpf(cpf);

        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getNome(), usuario.getEmail(),
                usuario.getCpf(), usuario.getPerfil(), usuario.getSenha());

        return Response.ok(usuarioDTO).build();
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
    @Path("/usuario/logado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterUsuarioLogado(@Context SecurityContext securityContext) {
        String cpfUsuarioLogado = securityContext.getUserPrincipal().getName();
        UsuarioDTO usuario = usuarioBO.buscarUsuarioPorCpf(cpfUsuarioLogado);

        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado").build();
        }
    }

}
