package com.dev.helpdesk.repository;

import com.dev.helpdesk.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {

    Iterable<Status> findByTicketIdOrderByDateChangeDesc(String ticketId); //Busca os status por ticket e ordena por data


}
