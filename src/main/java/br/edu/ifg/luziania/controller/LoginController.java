package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.entity.Usuario;
import br.edu.ifg.luziania.model.log.LogService;
import br.edu.ifg.luziania.model.log.LogType;
import br.edu.ifg.luziania.model.security.jwt.TokenUtils;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @Inject
    Template login;

    @Inject
    UsuarioBO usuarioBO;

    @Inject
    LogService logService;


    @GET
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getLogin() {
        return login.instance();
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("senha") String senha) throws Exception {
        Usuario usuario = usuarioBO.autenticarUsuario(username, senha);

        if (usuario != null) {
            String token = TokenUtils.generateToken(usuario);

            logService.registerLog(usuario.getId(), LogType.LOGIN, "Login bem-sucedido para o usuário: " + username);

            return Response.ok("{\"token\":\"" + token + "\"}").build();
        } else {
            logService.registerLog(null, LogType.ERROR, "Tentativa de login falha para o username: " + username);

            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email ou senha inválidos.")
                    .build();
        }
    }
}