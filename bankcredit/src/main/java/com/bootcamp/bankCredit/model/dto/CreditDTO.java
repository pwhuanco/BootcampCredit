package com.bootcamp.bankCredit.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreditDTO {

    private String id;
    private double capital;

    private LocalDateTime dateOperation = LocalDateTime.now();

    private String contractNumber;

    private String clientIdNumber;

    private double amountInitial;

    private double amount;

    private String chargeDay;

    private double commission;

    private double interestRate;

    private boolean debitor;

}
