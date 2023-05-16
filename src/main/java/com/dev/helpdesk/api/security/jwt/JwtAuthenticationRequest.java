package com.dev.helpdesk.api.security.jwt;

import lombok.Data;

import java.io.Serializable;


@Data
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

}
