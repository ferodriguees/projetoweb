package br.edu.ifg.luziania.controller.GerenciamentoDeSenhas;

import br.edu.ifg.luziania.model.bo.GerenciamentoDeSenhas.SenhaBO;
import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Senha;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/retirarSenha")
public class RetirarSenhaController {

    @Inject
    SenhaBO senhaBO;

    @Inject
    Template retirarSenha;

    @GET
    public TemplateInstance getRetirarSenhaPage() {
        return retirarSenha.instance();
    }

    @POST
    @Path("/gerar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gerarSenha(@QueryParam("tipo") String tipo) {
        try {
            Senha novaSenha = senhaBO.gerarSenha(tipo);
            return Response.ok(novaSenha).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Tipo de fila inválido. Use 'G' para Geral ou 'P' para Preferencial.")
                    .build();
        }
    }
}

