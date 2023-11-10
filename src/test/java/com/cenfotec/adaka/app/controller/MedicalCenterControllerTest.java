package com.cenfotec.adaka.app.controller;

import com.cenfotec.adaka.app.domain.*;
import com.cenfotec.adaka.app.service.MedicalCenterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(controllers = MedicalCenterController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MedicalCenterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MedicalCenterService medicalCenterService;
    @Autowired
    private ObjectMapper objectMapper;

    private Response<MedicalCenter> respuesta;
    private User user;
    private MedicalCenter medicalCenter;
    @BeforeEach
    public void Init(){
        user = User.builder().name("UserName")
                .role(Role.valueOf("ADMIN"))
                .status(Status.valueOf("ACTIVE"))
                .phone("88888888")
                .email("email@gmail.com")
                .password("password123").build();

        medicalCenter = MedicalCenter.builder()
                .email("email@gmail.com")
                .name("Centro Medico")
                .longitude("9°51'43")
                .latitude("83°55'14")
                .status(Status.valueOf("ACTIVE"))
                .direction("Cartago Centro")
                .user(user).build();
    }

    @Test
    public void MedicalCenterController_GetAllMedicalCentersByUserId_ReturnResponse() throws Exception {
        int medicalId = 1;
        when(medicalCenterService.getAllMedicalCentersByUserId(medicalId)).thenReturn(Arrays.asList(medicalCenter));

        ResultActions response = mockMvc.perform(get("/medical/all/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(respuesta)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(new Response<>("Éxito", Collections.singletonList(medicalCenter)))));

    }

}
