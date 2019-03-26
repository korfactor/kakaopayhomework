package com.kakaopay.homework.controller;


import com.kakaopay.homework.model.Agency;
import com.kakaopay.homework.model.dto.AgencyGetDTO;
import com.kakaopay.homework.model.dto.AgencyListResultDTO;
import com.kakaopay.homework.model.dto.FileDTO;
import com.kakaopay.homework.model.dto.FundDataDTO;
import com.kakaopay.homework.service.AgencyService;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class AgencyController {

    @Autowired
    private AgencyService agencyService;



    @PostMapping("/agency/listall")
    public List<AgencyListResultDTO> getAllAgency(){


        return agencyService.getAllAgencyAndCode();
    }



}
