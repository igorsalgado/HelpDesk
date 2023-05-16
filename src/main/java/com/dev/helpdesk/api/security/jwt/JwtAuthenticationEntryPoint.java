package com.dev.helpdesk.api.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {


    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException { // Este método é chamado sempre que um usuário tenta acessar um recurso REST protegido sem fornecer quaisquer credenciais ou com credenciais inválidas.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
