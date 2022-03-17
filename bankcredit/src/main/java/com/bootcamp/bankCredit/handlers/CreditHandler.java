package com.bootcamp.bankCredit.handlers;

import com.bootcamp.bankCredit.model.dto.ClientDTO;
import com.bootcamp.bankCredit.model.entities.Credit;
import com.bootcamp.bankCredit.service.CreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CreditHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditHandler.class);

    @Autowired
    private CreditService creditService;

    public Mono<ServerResponse> findAll(final ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(creditService.findAll(), Credit.class);
    }

    public Mono<ServerResponse> findCredit(final ServerRequest request) {
        String contractNumber = request.pathVariable("contractNumber");
        return creditService.findByContractNumber(contractNumber)
                .flatMap(c ->
                        ServerResponse.ok()
                                .body(BodyInserters.fromValue(c))
                                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> createCredit (final ServerRequest request){
        Mono<Credit> creditMono = request.bodyToMono(Credit.class);

        return creditMono.flatMap(credit ->{
            return creditService.getClient(credit.getClientIdNumber())
                    .flatMap(client -> {
                        LOGGER.info("Client:{} ", client.toString());
                        credit.setAmount(credit.getCapital()
                                +credit.getCapital()* credit.getInterestRate()
                                +credit.getCommission());
                        credit.setClient(ClientDTO.builder()
                                .name(client.getName())
                                .code(client.getClientType().getCode())
                                .clientIdNumber(client.getClientIdNumber())
                                .build());
                        credit.setAmountInitial(credit.getAmount());
                        return creditService.validateClientIdNumber(client.getClientIdNumber())
                                .flatMap(accountFound -> {
                                    if(accountFound.getClientIdNumber() != null
                                            &&(client.getClientType().getCode().equals("TP-01"))
                                            || client.getClientType().getCode().equals("TP-03")){
                                        LOGGER.info("La cuenta encontrada es: "
                                                +accountFound.getClientIdNumber());
                                        return Mono.empty();
                                    }else{
                                        return creditService.create(credit);
                                    }
                                });
                    });
        }).flatMap( c-> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c))
                .switchIfEmpty(ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> deleteCredit(final ServerRequest request){
        String id=request.pathVariable("id");
        Mono<Credit>creditMono=creditService.findById(id);
        return creditMono.doOnNext(c->LOGGER.info("Delete credit : {}",c.getId()))
                .flatMap(c->creditService.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


}
