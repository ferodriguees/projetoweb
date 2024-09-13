package br.edu.ifg.luziania.controller;

import br.edu.ifg.luziania.model.bo.UsuarioBO;
import br.edu.ifg.luziania.model.dto.UsuarioDTO;
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

//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance getSiteAdmin() {
//        // Obtém o nome do usuário logado a partir do token JWT
//        String nomeUsuario = securityIdentity.getPrincipal().getName();
//
//        // Passa o nome do usuário para o template
//        return siteAdmin.data("nomeUsuario", nomeUsuario);
//    }

    @Inject
    UsuarioBO usuarioBO;  // Supondo que você tenha a classe UsuarioBO para obter os dados do usuário

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getSiteAdmin() {
        // Obtém o nome do usuário logado a partir do token JWT
        String emailUsuario = securityIdentity.getPrincipal().getName();

        // Busca o usuário no banco de dados usando o email (ou outro identificador)
        UsuarioDTO usuarioDTO = usuarioBO.buscarUsuarioPorEmail(emailUsuario);

        // Verifica se o usuário foi encontrado
        if (usuarioDTO != null) {
            String perfil = usuarioDTO.getPerfil();  // Obtém o perfil do usuário logado

            // Passa o nome do usuário e o perfil para o template
            return siteAdmin
                    .data("nomeUsuario", usuarioDTO.getNome())
                    .data("perfilUsuario", perfil);
                    //.data("barraNav", barraNav.instance());  // Envia o perfil para o template

        }

        // Caso o usuário não seja encontrado, redireciona para uma página de erro
        return siteAdmin.data("mensagemErro", "Usuário não encontrado");
    }


}
