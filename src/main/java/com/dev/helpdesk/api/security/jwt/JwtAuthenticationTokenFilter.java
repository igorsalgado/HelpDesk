package com.dev.helpdesk.api.security.jwt;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtTokenUtil jwtTokenUtil;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException { // Este método é chamado para cada requisição. Ele é responsável por extrair o token do cabeçalho da requisição e validar o token.

        String authToken = request.getHeader("Authorization"); // O token é extraído do cabeçalho da requisição.
        String username = jwtTokenUtil.getUsernameFromToken(authToken); // O token é validado.

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); // Se o token for válido, o usuário é recuperado do banco de dados e uma instância de UserDetails é criada.
            if (jwtTokenUtil.validateToken(authToken, userDetails)) { // Se o token for válido, o usuário é autenticado.
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context"); // O usuário é autenticado e o contexto de segurança é definido.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response); // O filtro é encerrado.
    }
}
