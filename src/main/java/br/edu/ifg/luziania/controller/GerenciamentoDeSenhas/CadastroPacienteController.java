package br.edu.ifg.luziania.controller.GerenciamentoDeSenhas;

import br.edu.ifg.luziania.model.bo.GerenciamentoDeSenhas.AtendimentoBO;
import br.edu.ifg.luziania.model.dto.PacienteDTO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cadastro")
public class CadastroPacienteController {

    @Inject
    AtendimentoBO atendimentoBO;

    @Inject
    Template cadastroPaciente;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCadastroPacientePage() {
        return cadastroPaciente.instance();
    }

    @POST
    @Path("/realizarCadastro")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response realizarAtendimento(PacienteDTO pacienteDTO) {
        try {
            atendimentoBO.realizarAtendimento(pacienteDTO.getNome(), pacienteDTO.getCpf());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao realizar atendimento: " + e.getMessage())
                    .build();
        }
    }
}
