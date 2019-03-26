package com.kakaopay.homework.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class AnnualFunds {

    String year;
    Long total_amount;

    Map<String, Long> detail_amount;

    public AnnualFunds(String year, Long total_amount, Map<String, Long> detail_amount) {
        this.year = year;
        this.total_amount = total_amount;
        this.detail_amount = detail_amount;
    }

    public AnnualFunds(Long total_amount) {
        this.total_amount = total_amount;
    }
}
