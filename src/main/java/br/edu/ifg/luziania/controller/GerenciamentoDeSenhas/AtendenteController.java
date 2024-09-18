package br.edu.ifg.luziania.controller.GerenciamentoDeSenhas;

import br.edu.ifg.luziania.model.bo.GerenciamentoDeSenhas.SenhaBO;
import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Senha;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/atendimento")
public class AtendenteController {

    @Inject
    SenhaBO senhaBO;
    @Inject
    Template atendente;

    @GET
    public TemplateInstance getAtendentePage() {
        return atendente.instance();
    }

    @GET
    @Path("/chamar")
    @RolesAllowed({"admin", "atendente"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response chamarProximaSenha() {
        Senha proximaSenha = senhaBO.chamarProximaSenha();
        if (proximaSenha != null) {
            return Response.ok(proximaSenha).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Não há senhas na fila.")
                    .build();
        }
    }

}
