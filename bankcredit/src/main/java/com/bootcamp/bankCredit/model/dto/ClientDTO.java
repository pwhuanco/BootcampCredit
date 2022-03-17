package com.bootcamp.bankCredit.model.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClientDTO {
    private String name;
    private String code;
    private String clientIdNumber;
}