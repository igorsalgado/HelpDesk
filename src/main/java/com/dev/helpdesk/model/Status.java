package com.dev.helpdesk.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
public class Status {

    @Id
    private String id;

    @DBRef
    private Ticket ticket;

    @DBRef
    private User userChange;

    private Date dateChange;


}
