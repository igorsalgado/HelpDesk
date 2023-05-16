package com.dev.helpdesk.api.security.config;


import com.dev.helpdesk.api.security.jwt.JwtAuthenticationEntryPoint;
import com.dev.helpdesk.api.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Esta anotação habilita a segurança baseada em anotações.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler; // Esta classe é responsável por retornar um erro 401 (Unauthorized) sempre que um usuário tentar acessar um recurso protegido sem fornecer as credenciais ou com credenciais inválidas.

    @Autowired
    private UserDetailsService userDetailsService; // Esta classe é responsável por carregar os dados do usuário que está tentando se autenticar.

    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception { // Este método é responsável por definir o algoritmo de hash de senha e o UserDetailsService que será usado para carregar os dados do usuário que está tentando se autenticar.
        authenticationManagerBuilder.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Este método retorna uma instância de BCryptPasswordEncoder que é o algoritmo de hash de senha que será usado para codificar a senha do usuário.
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception { // Este método retorna uma instância de JwtAuthenticationTokenFilter que é o filtro responsável por extrair o token do cabeçalho da requisição e validar o token.
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // Este método configura o JwtAuthenticationEntryPoint que é a classe responsável por retornar um erro 401 (Unauthorized) sempre que um usuário tentar acessar um recurso protegido sem fornecer as credenciais ou com credenciais inválidas.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Este método configura o SessionCreationPolicy.STATELESS que é uma política de criação de sessão que não armazena sessão.
                .authorizeRequests() // Este método configura as URLs que precisam de autenticação.
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js" // Este método configura as URLs que podem ser acessadas sem autenticação.
                ).permitAll()
                .antMatchers("/api/auth/**").permitAll() // Este método configura as URLs que podem ser acessadas sem autenticação.
                .anyRequest().authenticated(); // Este método configura as URLs que precisam de autenticação.
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class); // Este método configura o filtro que será executado antes do filtro de autenticação padrão do Spring Security.
        httpSecurity.headers().cacheControl(); // Este método configura o cacheControl para desabilitar o cache.
    }



}
