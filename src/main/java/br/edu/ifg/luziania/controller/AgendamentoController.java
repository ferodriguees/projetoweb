package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.dao.AgendamentoDAO;
import br.edu.ifg.luziania.model.entity.Agendamento;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalTime;

@Path("/home")
public class AgendamentoController {

    //Agendamento com alguns problemas, implementar a qualquer momento
    @Inject
    AgendamentoDAO agendamentoDAO;
    @Inject
    Template agendarConsulta;

    @GET
    @Path("/pagina-consulta")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getAgendarConsulta() {
        return agendarConsulta.instance();
    }

    @POST
    @Path("/agendar-consulta")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response agendarConsulta(@FormParam("nome") String nome,
                                    @FormParam("cpf") String cpf,
                                    @FormParam("data") String data,
                                    @FormParam("hora") String hora) {
        Agendamento agendamento = new Agendamento();
        agendamento.setNome(nome);
        agendamento.setCpf(cpf);
        agendamento.setDataConsulta(LocalDate.parse(data));
        agendamento.setHoraConsulta(LocalTime.parse(hora));

        agendamentoDAO.salvar(agendamento);

        return Response.ok("Agendamento realizado com sucesso!").build();
    }
}
