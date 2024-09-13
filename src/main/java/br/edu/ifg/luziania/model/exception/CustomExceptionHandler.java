//package br.edu.ifg.luziania.model.exception;
//
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.ExceptionMapper;
//import jakarta.ws.rs.ext.Provider;
//import jakarta.ws.rs.ForbiddenException;
//import jakarta.ws.rs.NotAuthorizedException;
//
//@Provider
//public class CustomExceptionHandler implements ExceptionMapper<Exception> {
//
//    @Override
//    public Response toResponse(Exception exception) {
//        if (exception instanceof ForbiddenException) {
//            // Lida com exceções Forbidden (403 - Acesso negado)
//            return Response.status(Response.Status.FORBIDDEN)
//                    .entity("Você não tem autorização para acessar essa funcionalidade. Entre em contato com o Administrador.")
//                    .build();
//        } else if (exception instanceof NotAuthorizedException) {
//            // Lida com exceções NotAuthorized (401 - Não autorizado)
//            return Response.status(Response.Status.UNAUTHORIZED)
//                    .entity("Você precisa estar logado para acessar esta funcionalidade.")
//                    .build();
//        } else {
//            // Lida com outras exceções genéricas
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.")
//                    .build();
//        }
//    }
//}
