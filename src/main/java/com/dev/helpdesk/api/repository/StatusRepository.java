package com.dev.helpdesk.api.repository;

import com.dev.helpdesk.api.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {

    Iterable<Status> findByTicketIdOrderByDateChangeDesc(String ticketId); //Busca os status por ticket e ordena por data


}
