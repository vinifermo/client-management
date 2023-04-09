package com.desafio.squad.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration
public abstract class AbstractControllerTest {

    protected MockMvc mockMvc;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected void registerController(Object controller) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

}
