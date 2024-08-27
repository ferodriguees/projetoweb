package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response cadastrarUsuario(UsuarioDTO usuarioDTO) {
        // Chama o método do BO para cadastrar o usuário
        usuarioBO.cadastrarUsuario(usuarioDTO);

        // Redireciona para a página de login após o cadastro bem-sucedido
        return Response.seeOther(URI.create("/login")).build();
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
}
