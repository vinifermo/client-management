package com.desafio.squad.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration
public abstract class AbstractServiceTest {

    protected MockMvc mockMvc;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    protected void registerService(Object service) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(service)
                .build();
    }
}
