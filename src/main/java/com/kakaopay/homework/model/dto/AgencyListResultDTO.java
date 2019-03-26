package com.kakaopay.homework.model.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AgencyListResultDTO {


    String name;

    String code;


    public AgencyListResultDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
