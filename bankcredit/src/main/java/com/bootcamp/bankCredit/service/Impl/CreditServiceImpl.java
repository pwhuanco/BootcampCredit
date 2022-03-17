package com.bootcamp.bankCredit.service.Impl;

import com.bootcamp.bankCredit.model.dto.Client;
import com.bootcamp.bankCredit.model.entities.Credit;
import com.bootcamp.bankCredit.repository.CreditRepository;
import com.bootcamp.bankCredit.service.CreditService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {

    private static final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);
    private final WebClient webClient;
    private final CreditRepository creditRepository;

    @Override
    public Mono<Credit> findByContractNumber(String contractNumber) {
        return creditRepository.findByContractNumber(contractNumber);
    }

    @Override
    public Mono<Client> getClient(String clientIdNumber) {
        return webClient.get()
                .uri("/findClientCredit/"+clientIdNumber)
                .retrieve()
                .bodyToMono(Client.class)
                .doOnNext(c->log.info("Client response : {}",c.getName()));
    }

    @Override
    public Flux<Credit> findAllByClientIdNumber(String customerIdNumber) {
        return creditRepository.findAllByClientIdNumber(customerIdNumber);
    }

    @Override
    public Mono<Credit> validateClientIdNumber(String clientIdNumber) {
        return creditRepository.findByClientIdNumber(clientIdNumber)
                .switchIfEmpty(Mono.just(Credit.builder().clientIdNumber(null).build()));
    }


    @Override
    public Mono<Credit> create(Credit obj) {
        return creditRepository.save(obj);
    }

    @Override
    public Flux<Credit> findAll() {
        return creditRepository.findAll();
    }

    @Override
    public Mono<Credit> findById(String id) {
        return creditRepository.findById(id);
    }

    @Override
    public Mono<Credit> update(Credit obj) {
        return creditRepository.save(obj);
    }

    @Override
    public Mono<Void> delete(Credit obj) {
        return creditRepository.delete(obj);
    }
}
