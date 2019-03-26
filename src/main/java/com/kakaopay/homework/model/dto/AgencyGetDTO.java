package com.kakaopay.homework.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AgencyGetDTO {

    @NonNull
    String name;
}
