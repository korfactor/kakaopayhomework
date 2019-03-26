package com.kakaopay.homework.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FundDataDTO {


    private String id;
    private Long year;
    private Integer month;
    private Long totalfund;
    private Long kookmin;
    private Long woori;
    private Long shinhan;
    private Long citi;
    private Long hana;
    private Long nh;
    private Long keb;
    private Long etc;

    public FundDataDTO(String id, Long year, Integer month, Long totalfund, Long kookmin, Long woori, Long shinhan, Long citi, Long hana, Long nh, Long keb, Long etc) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.totalfund = totalfund;
        this.kookmin = kookmin;
        this.woori = woori;
        this.shinhan = shinhan;
        this.citi = citi;
        this.hana = hana;
        this.nh = nh;
        this.keb = keb;
        this.etc = etc;
    }
}
