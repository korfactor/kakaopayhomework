package com.kakaopay.homework.fund.service;

import com.kakaopay.homework.repository.AgencyRepository;
import com.kakaopay.homework.repository.FundRepository;
import com.kakaopay.homework.service.AgencyService;
import com.kakaopay.homework.service.FundService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FundService.class})
public class FundServiceTest {

    String testFile="";

    @Before
    public void setUp() {
        testFile = "src/test/resources/test.txt";
    }
    @MockBean
    private FundRepository fundRepo;

    @MockBean
    private AgencyRepository agencyRepo;

    @Autowired
    private FundService fundService;


    @Test
    public void testsaveAgencyData(){

        this.fundService.saveAgencyData(testFile);


    }









}
