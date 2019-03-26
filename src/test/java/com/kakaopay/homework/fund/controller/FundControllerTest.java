package com.kakaopay.homework.fund.controller;


import com.kakaopay.homework.controller.AgencyController;
import com.kakaopay.homework.controller.FundController;
import com.kakaopay.homework.service.AgencyService;
import com.kakaopay.homework.service.FundService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(FundController.class)
public class FundControllerTest {

    String testFile="";

    @Autowired
    MockMvc mvc;

    @MockBean
    private FundService fundService;

    @Before
    public void setUp() {
        testFile = "src/test/resources/test.txt";
    }


    @Test
    public void test_handleFileUpload() throws Exception{
        JSONObject testJson = new JSONObject();
        testJson.put("fildName",testFile);


        ResultActions result = this.mvc.perform(post("/fund/upload").contentType(MediaType.APPLICATION_JSON).content(testJson.toString())
        );

        result.andExpect(
                status().is2xxSuccessful()
        );
    }
}
