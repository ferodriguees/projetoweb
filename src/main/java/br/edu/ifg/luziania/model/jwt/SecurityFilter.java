//package br.edu.ifg.luziania.model.jwt;
//
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.core.Context;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.core.SecurityContext;
//import jakarta.ws.rs.ext.Provider;
//
//import java.io.IOException;
//
//@Provider
//public class SecurityFilter implements ContainerRequestFilter {
//
//    @Context
//    SecurityContext securityContext;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        // Verifica se o usuário está autenticado e autorizado
//        if (securityContext.getUserPrincipal() != null && !securityContext.isUserInRole("admin")) {
//            // Redireciona para página de acesso negado
//            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
//        }
//    }
//}
//
