package com.dev.helpdesk.security.jwt;

import com.dev.helpdesk.enums.ProfileEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory { // Classe factory que constroi o JwtUser

    private JwtUserFactory() {
    }

    public static JwtUser create(JwtUser jwtUser) { // Converte e gera um JwtUser com base nos dados de um usuario
        return new JwtUser(
                jwtUser.getId(),
                jwtUser.getUsername(),
                jwtUser.getPassword(),
                jwtUser.getAuthorities());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) { // Converte o perfil do usuario para o formato utilizado pelo Spring Security
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }
}
