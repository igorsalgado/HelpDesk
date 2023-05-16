package com.dev.helpdesk.api.security.jwt;

import com.dev.helpdesk.api.model.enums.ProfileEnum;
import com.dev.helpdesk.api.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory { // Classe factory que constroi o JwtUser

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) { // Cria o JwtUser com base no usuario do banco de dados
        return new JwtUser(user.getId(),
                            user.getEmail(),
                            user.getPassword(),
                            mapToGrantedAuthorities(user.getProfile()));

    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) { // Converte o perfil do usuario para o formato utilizado pelo Spring Security
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }
}
