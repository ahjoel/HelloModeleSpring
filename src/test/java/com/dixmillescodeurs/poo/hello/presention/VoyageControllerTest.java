package com.dixmillescodeurs.poo.hello.presention;

import com.dixmillescodeurs.poo.hello.model.dto.VoyageDto;
import com.dixmillescodeurs.poo.hello.rest.ressource.VoyageControllerRest;
import com.dixmillescodeurs.poo.hello.service.VoyageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class VoyageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VoyageService voyageService;

    @InjectMocks
    private VoyageControllerRest voyageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voyageController).build();
    }


    @Test
    void listevoyages() throws Exception {
        VoyageDto voyageDto1 = VoyageDto.builder().nom("KRIBI").destination("FRANCE").description("PARIS").build();
        VoyageDto voyageDto2 = VoyageDto.builder().nom("TEST").destination("TOGO").description("LOME").build();
        List<VoyageDto> voyageDtoList = Arrays.asList(voyageDto1, voyageDto2);

        when(voyageService.listVoyages()).thenReturn(voyageDtoList);

        mockMvc.perform(get("/api/voyage/getall"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(voyageService, times(1)).listVoyages();
        verifyNoMoreInteractions(voyageService);
    }
}