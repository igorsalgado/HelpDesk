package com.dev.helpdesk.api.repository;

import com.dev.helpdesk.api.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    Page<Ticket> findByUserIdOrderByDateDesc(Pageable pages, String userId); //Busca todos os tickets de um usuario e ordena por data

    Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc(
            String title, String status, String priority, Pageable pages); //Busca os tickets por titulo, status e prioridade e ordena por data

    Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc(
            String title, String status, String priority, String userId, Pageable pages); //Busca todos os tickets por titulo, status e prioridade de um usuario e ordena por data

    Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssignedUserIdOrderByDateDesc(
            String title, String status, String priority, String assignedUserId, Pageable pages); //Busca todos os tickets por titulo, status e prioridade de um tecnico e ordena por data

    Page<Ticket> findByNumber(Integer number, Pageable pages); //Busca os tickets por numero
}
