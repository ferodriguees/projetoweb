package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Optional;

@Path("/usuario")
public class UsuarioController {

    @Inject
    UsuarioBO usuarioBO;
    @Inject
    Template cadastroAdmin;

    @GET
    @Path("/cadastroAdmin")
    public TemplateInstance getCadastroAdmin() {
        return cadastroAdmin.instance();
    }

    @GET
    @Path("/{id}")
    public Response buscarUsuarioPorId(@PathParam("id") Long id) {
        Optional<Usuario> usuario = usuarioBO.buscarUsuarioPorId(id);
        return usuario.isPresent() ? Response.ok(usuario.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }


    @POST
    @Path("/cadastroAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioDTO usuarioDTO) {
        // Chama o método do BO para cadastrar o usuário
        usuarioBO.cadastrarAdmin(usuarioDTO);

        return Response.ok("Usuário cadastrado com sucesso").build();
        //return Response.seeOther(URI.create("/login")).build();
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/delete/{id}")
    public Response deletarUsuario(@PathParam("id") Long id) {
        usuarioBO.deletarUsuario(id);
        return Response.noContent().build();
    }

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario() {
        JsonWebToken jwt = (JsonWebToken) securityContext.getUserPrincipal();
        String nome = jwt.getClaim("nome");
        String cpf = jwt.getClaim("cpf");
        String email = jwt.getClaim("email");
        String perfil = jwt.getClaim("perfil");
        String username = jwt.getClaim("username");

        // Cria um objeto JSON para retornar as informações
        JsonObject usuarioJson = Json.createObjectBuilder()
                .add("nome", nome)
                .add("cpf", cpf)
                .add("email", email)
                .add("perfil", perfil)
                .add("username", username)
                .build();

        return Response.ok(usuarioJson).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getAllUsuarios() {
        return usuarioBO.findAll(); // Assumindo que você tenha esse método no BO
    }
}
