package com.kakaopay.homework.controller;


import com.kakaopay.homework.model.Agency;
import com.kakaopay.homework.model.dto.AgencyGetDTO;
import com.kakaopay.homework.model.dto.AnnualFunds;
import com.kakaopay.homework.model.dto.FileDTO;
import com.kakaopay.homework.model.dto.FundPartsDTO;
import com.kakaopay.homework.service.AgencyService;
import com.kakaopay.homework.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class FundController {

    @Autowired
    private FundService fundService;


    @PostMapping("/fund/upload")
    public Map<String,String> handleFileUpload(@RequestBody FileDTO fileDTO) throws Exception {


        String line = "";

        Map<String,String> result = fundService.saveAgencyData(fileDTO.getFileName());


        return result;

    }

    @PostMapping("/fund/maxinstitute")
    public Map<String,String> getMaximumInstitute() throws Exception {


        Map<String,String> result = fundService.getMaximumAgency();


        return result;

    }

    @PostMapping("/fund/min-max-keb")
    public FundPartsDTO.ResponseMinMaxFundDto getMinMaxKeb() throws Exception {


        FundPartsDTO.ResponseMinMaxFundDto result = fundService.getMinMaxKeb();


        return result;

    }

    @PostMapping("/fund/annualfund")
    public List<AnnualFunds> getAnnualFund() throws Exception {

        List<AnnualFunds> resultList = fundService.getAnnualFunds();
        return resultList;
    }
    @GetMapping("/fund/forcast")
    public FundPartsDTO.foreCastFundDto getForCaseResult(@RequestParam String bank,@RequestParam int month){

        return fundService.getForecastResult(bank,month);
    }



}
