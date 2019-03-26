package com.kakaopay.homework.agency.controller;


import com.kakaopay.homework.controller.AgencyController;
import com.kakaopay.homework.model.Agency;
import com.kakaopay.homework.service.AgencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(AgencyController.class)
public class AgencyControllerTest {


    @Autowired
    MockMvc mvc;

    @MockBean
    private AgencyService agencyService;

    @Test
    public void test_getAllAgency() throws Exception{

        ResultActions result = this.mvc.perform(post("/agency/listall")
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );


        result.andExpect(
                status().is2xxSuccessful())
        ;
        //return agencyService.getAllAgencyAndCode();
    }
}
