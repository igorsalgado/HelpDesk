package com.dev.helpdesk.model;

import com.dev.helpdesk.enums.PriorityEnum;
import com.dev.helpdesk.enums.StatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
public class Ticket {

    @Id
    private String id;

    @DBRef(lazy = true) //Referencia a classe User
    private User user;

    private Date date;

    private String title;

    private Integer number;

    private StatusEnum status;

    private PriorityEnum priority;

    @DBRef(lazy = true)
    private User assignedUser;

    private String description;

    private String image;

    @Transient //Não persiste no banco de dados
    private List<Status> changes;

}
