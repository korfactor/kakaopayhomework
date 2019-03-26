package com.kakaopay.homework.model.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
public class FundPartsDTO {

    @Getter
    @Setter
    public static class ResponseMaximumFundDto extends FundPartsDTO{

        private long year;
        private String bank;

    }

    @Getter
    @Setter
    public static class ResponseMinMaxFundDto extends FundPartsDTO{

        private String bank;
        private List<Map<String,String>> support_amount;

    }

    @Getter
    @Setter
    public static class forCalcMaximumFundDto extends FundPartsDTO{

        private int year;
        private String bank;
        private long amounts;

    }
    @Getter
    @Setter
    public static class foreCastFundDto extends FundPartsDTO{
        private int year;
        private int month;
        private String bank;
        private long amounts;
    }

}
