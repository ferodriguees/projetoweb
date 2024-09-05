package br.edu.ifg.luziania.controller;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/site_admin")
public class SiteAdminController {

    @Inject
    Template siteAdmin;
    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getSiteAdmin() {
        String nomeUsuario = securityIdentity.getPrincipal().getName();
        return siteAdmin.data("nomeUsuario", nomeUsuario);
    }
}
