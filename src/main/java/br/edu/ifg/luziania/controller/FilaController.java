package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.FilaBO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fila")
public class FilaController {

    @Inject
    FilaBO filaBO;

    @Inject
    Template telaPrincipal;

    @GET
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getTelaPrincipal() {
        return telaPrincipal.instance();
    }

    @POST
    @Path("/chamar-proxima-senha")
    @Produces(MediaType.APPLICATION_JSON)
    public Response chamarProximaSenha() {
        String senha = filaBO.chamarProximaSenha();
        if (senha != null) {
            return Response.ok().entity("{\"senha\": \"" + senha + "\"}").build();
        } else {
            return Response.ok().entity("{\"message\": \"Nenhuma senha disponível.\"}").build();
        }
    }
}
