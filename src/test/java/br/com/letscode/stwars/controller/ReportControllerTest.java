package br.com.letscode.stwars.controller;

import br.com.letscode.stwars.dto.ReportRequestDto;
import br.com.letscode.stwars.service.MarketPlaceService;
import br.com.letscode.stwars.service.ReportService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ReportController controller;

    @Mock
    private ReportService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void reportPerson() throws Exception {
        ReportRequestDto requestDto = new ReportRequestDto();
        requestDto.setReportPersonId(10L);

        mockMvc.perform(post("/report/11").content(asJsonString(requestDto)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}