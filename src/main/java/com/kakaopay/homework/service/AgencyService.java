package com.kakaopay.homework.service;


import com.kakaopay.homework.model.Agency;
import com.kakaopay.homework.model.dto.AgencyListResultDTO;
import com.kakaopay.homework.repository.AgencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepo;


    @Transactional(readOnly = true)
    public Agency getAgency(String institute_name){


        return agencyRepo.findByName(institute_name);

    }


    @Transactional(readOnly = true)
    public List<AgencyListResultDTO> getAllAgencyAndCode(){
        log.info("find All Agency List");
        List<Agency> agencyList = (List<Agency>) agencyRepo.findAll();

        List<AgencyListResultDTO> resultList = new ArrayList<>();

        for(Agency value : agencyList){
            AgencyListResultDTO tmpDTO = new AgencyListResultDTO();
            tmpDTO.setCode(value.getCode());
            tmpDTO.setName(value.getName());
            resultList.add(tmpDTO);
        }

        return resultList;

    }
    @Transactional(readOnly = true)
    public List<String> getAllAgency(){

        List<String> agencyList =agencyRepo.findAllInstituename();
        return agencyList;

    }






}
