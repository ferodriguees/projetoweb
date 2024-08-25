package br.edu.ifg.luziania.model.security.jwt;

import java.util.Arrays;
import java.util.HashSet;

import br.edu.ifg.luziania.model.entity.Usuario;

import io.smallrye.jwt.build.Jwt;

public class GenerateToken {
    /**
     * Generate JWT token
     */
    public static String generate(Usuario usuario) {
        return Jwt.issuer("http://localhost:8080")
                .groups(new HashSet<>(Arrays.asList("Usuario", "Admin")))
                .claim("nome", usuario.getNome())
                .sign();
    }
}