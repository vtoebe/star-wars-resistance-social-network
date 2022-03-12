package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.MarketPlaceDto;
import br.com.letscode.stwars.dto.PersonIdDto;
import br.com.letscode.stwars.service.MarketPlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MarketPlaceControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MarketPlaceController controller;

    @Mock
    private MarketPlaceService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void insertOffer() throws Exception {
        MarketPlaceDto marketPlaceDto = new MarketPlaceDto();
        marketPlaceDto.setIdPerson(10L);

        mockMvc.perform(post("/marketplace/create-offer").content(asJsonString(marketPlaceDto)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    void getListByMarketPlace() throws Exception {
        mockMvc.perform(get("/marketplace/offers").contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    void tradeItems() throws Exception {
        PersonIdDto personIdDto = new PersonIdDto();
        personIdDto.setId(10L);

        mockMvc.perform(patch("/marketplace/offers/10/trade").content(asJsonString(personIdDto)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}