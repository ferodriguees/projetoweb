package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.entity.Usuario;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    Template login;

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLogin() {
        // Para renderizar a página de login
        return login.instance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("email") String email, @FormParam("senha") String senha) {
        Usuario usuario = usuarioBO.autenticarUsuario(email, senha);

        if (usuario != null) {
            // Gera o token JWT após autenticar o usuário
            String token = usuarioBO.gerarTokenJWT(usuario);
            return Response.ok("{\"token\":\"" + token + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email ou senha inválidos.")
                    .build();
        }
    }
}