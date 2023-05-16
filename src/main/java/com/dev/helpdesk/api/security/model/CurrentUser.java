package com.dev.helpdesk.api.security.model;

import com.dev.helpdesk.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {

    private String token;
    private User user;




}
