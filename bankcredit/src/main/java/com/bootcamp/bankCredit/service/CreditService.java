package com.bootcamp.bankCredit.service;

import com.bootcamp.bankCredit.model.dto.Client;
import com.bootcamp.bankCredit.model.entities.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CreditService extends CrudService<Credit,String>{


    Mono<Credit> findByContractNumber(String contractNumber);

    Mono<Client> getClient(String clientIdNumber);

    Flux<Credit> findAllByClientIdNumber(String clientIdNumber);

    Mono<Credit> validateClientIdNumber(String clientIdNumber);

}
