package com.bootcamp.bankCredit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String name;
    private String clientIdType;
    private String clientIdNumber;
    private ClientType clientType;
}
