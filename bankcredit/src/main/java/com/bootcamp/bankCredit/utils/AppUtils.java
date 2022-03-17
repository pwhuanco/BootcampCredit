package com.bootcamp.bankCredit.utils;


import com.bootcamp.bankCredit.model.dto.Client;
import com.bootcamp.bankCredit.model.dto.CreditDTO;
import com.bootcamp.bankCredit.model.entities.Credit;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static CreditDTO entityToDto(Credit credit){
        CreditDTO creditDTO= new CreditDTO();
        BeanUtils.copyProperties(credit,creditDTO);
        return creditDTO;
    }
    public static Credit DtoToEntity(CreditDTO creditDTO){
        Credit credit = new Credit();
        BeanUtils.copyProperties(creditDTO, credit);
        return credit;
    }
}
