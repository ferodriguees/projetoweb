package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.GerenciamentoDeSenhas.AtendimentoBO;
import br.edu.ifg.luziania.model.entity.GerenciamentoDeSenhas.Paciente;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/medico")
public class MedicoController {

    @Inject
    Template medico;

    @Inject
    AtendimentoBO fila;

    @Inject
    Template laudoMedico;

    @Inject
    AtendimentoBO atendimentoBO;

    @GET
    @Path("/tela")
    public TemplateInstance getTelaMedico() {
        return medico.instance();
    }

    @GET
    @Path("/laudoMedico")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getTelaLaudo() {
        return laudoMedico.instance();
    }

    @GET
    @Path("/chamarPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Response chamarPaciente() {
        Paciente proximoPaciente = fila.chamarPaciente();
        if (proximoPaciente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Nenhum paciente na fila\"}")
                    .build();
        }
        return Response.ok(proximoPaciente).build();
    }

    @POST
    @Path("/realizarAtendimento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response realizarAtendimento(Map<String, String> dadosAtendimento) {
        String laudo = dadosAtendimento.get("laudo");
        String atestado = dadosAtendimento.get("atestado");

        return Response.ok("Atendimento realizado com sucesso").build();
    }


    @POST
    @Path("/marcarAusente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response marcarPacienteAusente(Paciente paciente) {
        atendimentoBO.marcarPacienteComoAusente();
        return Response.ok("Paciente removido da fila como ausente").build();
    }
}
