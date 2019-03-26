package com.kakaopay.homework.service;


import com.kakaopay.homework.model.Agency;
import com.kakaopay.homework.model.HouseFund;

import com.kakaopay.homework.model.dto.AnnualFunds;
import com.kakaopay.homework.model.dto.FundDataDTO;
import com.kakaopay.homework.model.dto.FundPartsDTO;
import com.kakaopay.homework.repository.AgencyRepository;
import com.kakaopay.homework.repository.FundRepository;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class FundService {


    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String KEB = "외환은행";

    @Autowired
    private FundRepository fundRepo;

    @Autowired
    private AgencyRepository agencyRepo;


    @Transactional
    public List<AnnualFunds> getAnnualFunds(){
        List<AnnualFunds> resultList = new ArrayList<>();

        List<Integer> yearList = fundRepo.findDistinctByYear();
        List<String> agencyList = agencyRepo.findAllInstituename();

        for(int year : yearList){

            AnnualFunds tmpFund = new AnnualFunds(0L);
            Map<String,Long> tmpMap = new LinkedHashMap<>();
            for(String agency : agencyList) {
                List<HouseFund> tmpResultList = fundRepo.findByYearAndBank(year, agency);



                tmpFund.setYear(String.valueOf(year));
                long tmpBudget = 0;
                for (int i = 0; i < tmpResultList.size(); i++) {

                    tmpFund.setTotal_amount(tmpFund.getTotal_amount() + tmpResultList.get(i).getBudget());
                    tmpBudget += tmpResultList.get(i).getBudget();
                }

                tmpMap.put(agency,tmpBudget);



            }
            tmpFund.setDetail_amount(tmpMap);
            resultList.add(tmpFund);


        }




        return resultList;
    }

    @Transactional
    public FundPartsDTO.foreCastFundDto getForecastResult(String bank, int month){

        FundPartsDTO.foreCastFundDto resultForecast = new FundPartsDTO.foreCastFundDto();

        double[] wholeBudgetList = fundRepo.getWholeBudget(bank);
        int forecastSize = month + 2;
        int p = 2;
        int d = 1;
        int q = 1;
        int P = 1;
        int D = 1;
        int Q = 0;
        int m = 0;

        ArimaParams params = new ArimaParams(p, d, q, P, D, Q, m);

        ForecastResult forecastResult = Arima.forecast_arima(wholeBudgetList, forecastSize, params);
        double[] forecastData = forecastResult.getForecast();

        List<Agency> agencyList = (List<Agency>) agencyRepo.findAll();

        String bankCode = "";

        for(Agency agency : agencyList){
            if(bank.equals(agency.getName())){
                bankCode = agency.getCode();
            }
        }

        resultForecast.setAmounts(Math.round(forecastData[forecastData.length-1]));
        resultForecast.setBank(bankCode);
        resultForecast.setYear(2018);
        resultForecast.setMonth(month);

       // ForecastResult forecastResult = Arima.forecast_arima(wholeBudgetList, forecastSize, p, d, q, P, D, Q, m);


        return resultForecast;

    }

    @Transactional
    public Map<String,String> getMaximumAgency(){
        Map<String,String> resultMap = new LinkedHashMap<>();

        try {
            List<Integer> yearList = fundRepo.findDistinctByYear();
            List<String> agencyList = agencyRepo.findAllInstituename();
            List<FundPartsDTO.forCalcMaximumFundDto> maxList = new ArrayList<>();
            for (int year : yearList) {

                String bank = "";
                long amounts = 0;
                FundPartsDTO.forCalcMaximumFundDto tmpMaxData = new FundPartsDTO.forCalcMaximumFundDto();
                for (int i = 0; i < agencyList.size(); i++) {

                    if (i == 0) {
                        bank = agencyList.get(i);
                        amounts = fundRepo.sumBudgetAnnual(year, agencyList.get(i));

                    } else {

                        if (fundRepo.sumBudgetAnnual(year, agencyList.get(i)) > amounts) {
                            bank = agencyList.get(i);
                            amounts = fundRepo.sumBudgetAnnual(year, agencyList.get(i));
                        }

                    }
                }
                tmpMaxData.setYear(year);
                tmpMaxData.setAmounts(amounts);
                tmpMaxData.setBank(bank);

                maxList.add(tmpMaxData);


            }

            Collections.sort(maxList, new Comparator<FundPartsDTO.forCalcMaximumFundDto>() {
                @Override
                public int compare(FundPartsDTO.forCalcMaximumFundDto o1, FundPartsDTO.forCalcMaximumFundDto o2) {

                    if (o1.getAmounts() > o2.getAmounts()) {
                        return 1;
                    } else if (o1.getAmounts() < o2.getAmounts()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            Collections.reverse(maxList);

            resultMap.put("year", String.valueOf(maxList.get(0).getYear()));
            resultMap.put("bank", maxList.get(0).getBank());
        }catch (Exception e){
            resultMap.put("RESULT",FAILURE);
        }
        return resultMap;
    }


    @Transactional
    public FundPartsDTO.ResponseMinMaxFundDto getMinMaxKeb(){

        FundPartsDTO.ResponseMinMaxFundDto resultMinMax = new FundPartsDTO.ResponseMinMaxFundDto();
        resultMinMax.setBank(KEB);
        List<Map<String,String>> avgList = new ArrayList<>();
        Map<String,String> resultMap = new LinkedHashMap<>();

        try {
            List<Integer> yearList = fundRepo.findDistinctByYear();
            List<String> agencyList = agencyRepo.findAllInstituename();
            List<FundPartsDTO.forCalcMaximumFundDto> maxList = new ArrayList<>();
            for (int year : yearList) {

                String bank = "";
                long amounts = 0;
                FundPartsDTO.forCalcMaximumFundDto tmpMaxData = new FundPartsDTO.forCalcMaximumFundDto();

                int countMonth = fundRepo.findMonthDistinctByYear(year,KEB);
                long sumBudget = fundRepo.sumBudgetAnnual(year,KEB);
                long avg = sumBudget/countMonth;

                tmpMaxData.setYear(year);
                tmpMaxData.setAmounts(avg);
                tmpMaxData.setBank(bank);

                maxList.add(tmpMaxData);


            }

            Collections.sort(maxList, new Comparator<FundPartsDTO.forCalcMaximumFundDto>() {
                @Override
                public int compare(FundPartsDTO.forCalcMaximumFundDto o1, FundPartsDTO.forCalcMaximumFundDto o2) {

                    if (o1.getAmounts() > o2.getAmounts()) {
                        return 1;
                    } else if (o1.getAmounts() < o2.getAmounts()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            Collections.reverse(maxList);





            Map<String,String> minMap = new LinkedHashMap<>();
            minMap.put("year",String.valueOf(maxList.get(maxList.size()-1).getYear()));
            minMap.put("amout",String.valueOf(maxList.get(maxList.size()-1).getAmounts()));
            avgList.add(minMap);


            Map<String,String> maxMap = new LinkedHashMap<>();
            maxMap.put("year",String.valueOf(maxList.get(0).getYear()));
            maxMap.put("amout",String.valueOf(maxList.get(0).getAmounts()));
            avgList.add(maxMap);
            resultMinMax.setSupport_amount(avgList);
        }catch (Exception e){
            resultMap.put("RESULT",FAILURE);
            avgList.add(resultMap);
            resultMinMax.setSupport_amount(avgList);

            return resultMinMax;
        }


        return resultMinMax;
    }



    @Transactional
    public Map<String,String> saveAgencyData(String fileName){

        String line = "";
        Map<String,String> result = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            if(br.readLine() == null) {

                result.put("RESULT",FAILURE);
                return result;
            }

            List<HouseFund> tmpImportList = new ArrayList<>();

            while ((line = br.readLine()) != null) {

                String[] dataArr = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);


                for (int i = 2; i < dataArr.length; i++) {
                    if (StringUtils.isEmpty(dataArr[i]) == true)
                        break;

                    HouseFund tmpData = new HouseFund();

                    dataArr[i] = dataArr[i].replaceAll(",", "").replaceAll("\"", "");



                    tmpData.setYear(Integer.parseInt(dataArr[0]));
                    tmpData.setMonth(Integer.parseInt(dataArr[1]));
                    if(i==2){
                        tmpData.setBank("주택도시기금");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==3){
                        tmpData.setBank("국민은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==4){
                        tmpData.setBank("우리은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==5){
                        tmpData.setBank("신한은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==6){
                        tmpData.setBank("한국시티은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==7){
                        tmpData.setBank("하나은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==8){
                        tmpData.setBank("농협은행/수협은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==9){
                        tmpData.setBank("외환은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }else if(i==10){
                        tmpData.setBank("기타은행");
                        tmpData.setBudget(Long.parseLong(dataArr[i]));
                    }

                    fundRepo.save(tmpData);
                    tmpData = null;


                }


            }



        }catch (Exception e) {

            result.put("RESULT",FAILURE);
            return result;
        }
    result.put("RESULT",SUCCESS);
    return result;
    }
}
