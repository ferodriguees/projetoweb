package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/home")
public class HomeController {

    @Inject
    Template telaInicial;
    @Inject
    Template nossaClinica;
    @Inject
    Template corpoClinico;
    @Inject
    Template convenios;

    @GET
    @Path("/tela-inicial")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getTelaInicial() {
        return telaInicial.instance();
    }

    @GET
    @Path("/nossa-clinica")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getNossaClinica() {
        return nossaClinica.instance();
    }

    @GET
    @Path("/corpo-clinico")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCorpoClinico() {
        return corpoClinico.instance();
    }

    @GET
    @Path("/convenios")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getConvenios() {
        return convenios.instance();
    }


}

