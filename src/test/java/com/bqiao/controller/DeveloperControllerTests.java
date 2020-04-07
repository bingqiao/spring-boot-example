package com.bqiao.controller;

import com.bqiao.domain.Developer;
import com.bqiao.service.DeveloperService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeveloperController.class)
public class DeveloperControllerTests {

    private static final String TEST_EMAIL = "test-email";
    private static final String TEST_DEVELOPER_ID = "test-companyDevelopers-id";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeveloperService service;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void givenOneDeveloper_whenGetDevelopers_thenReturnJsonArray()
            throws Exception {

        Developer dev = new Developer();
        dev.setEmail(TEST_EMAIL);

        given(service.getDevelopers()).willReturn(Collections.singletonList(dev));

        mvc.perform(get("/developer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(TEST_EMAIL)));
    }

    @Test
    public void givenNewDeveloper_whenCreateDeveloper_thenReturnCreatedDeveloper()
            throws Exception {

        Developer toCreate = new Developer();
        toCreate.setEmail(TEST_EMAIL);
        Developer created = new Developer();
        created.setEmail(TEST_EMAIL);
        given(service.createDeveloper(toCreate)).willReturn(created);

        mvc.perform(post("/developer")
                .content(mapper.writeValueAsString(toCreate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
