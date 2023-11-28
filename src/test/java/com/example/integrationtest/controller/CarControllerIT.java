package com.example.integrationtest.controller;

import com.example.model.car.CreateCarCommand;
import com.example.model.car.UpdateCarCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CarControllerIT {


    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;
    private CreateCarCommand createCarCommand;
    private UpdateCarCommand updateCarCommand;

    @BeforeEach
    public void init() {
        createCarCommand = new CreateCarCommand();
        createCarCommand.setVin("WBA99345671");
        createCarCommand.setBrand("Volkswagen");
        createCarCommand.setModel("Passat");
        createCarCommand.setRegistration("KTA 22667");
        createCarCommand.setProductionYear(1998);
        //createCarCommand.setRentsId();
        createCarCommand.setIsAvailable(true);

        createCarCommand = new CreateCarCommand();
        createCarCommand.setVin("WBA88345671");
        createCarCommand.setBrand("Ford");
        createCarCommand.setModel("Mondeo");
        createCarCommand.setRegistration("KTA 14HR");
        createCarCommand.setProductionYear(2010);
        //createCarCommand.setRentsId();
        createCarCommand.setIsAvailable(true);

        updateCarCommand.setId(20L);
        updateCarCommand.setVin("XYZ99345671");
        updateCarCommand.setBrand("VW");
        updateCarCommand.setModel("Golf");
        updateCarCommand.setRegistration("WE 58890");
        updateCarCommand.setProductionYear(2000);
        //updateCarCommand.setRentsId();
        updateCarCommand.setIsAvailable(true);

        updateCarCommand.setId(30L);
        updateCarCommand.setVin("XYZ88345671");
        updateCarCommand.setBrand("Ford");
        updateCarCommand.setModel("Fusion");
        updateCarCommand.setRegistration("WE 555555");
        updateCarCommand.setProductionYear(2011);
        //updateCarCommand.setRentsId();
        updateCarCommand.setIsAvailable(true);
    }

    @Test
    void shouldReturnAllCars() throws Exception {
        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createCarCommand)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.content", notNullValue()))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].vin", equalTo("WBA99345671")))
                .andExpect(jsonPath("$.content.[0].productionYear", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].brand", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].model", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].mileage", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].registration", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].isAvailable", equalTo(1)))
                .andExpect(jsonPath("$.content.[0].isAvailable", equalTo(true)))
                .andExpect(jsonPath("$.content.[0].rents", equalTo(null)));

    }

}
