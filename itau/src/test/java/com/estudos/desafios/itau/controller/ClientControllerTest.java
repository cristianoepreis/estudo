package com.estudos.desafios.itau.controller;

import com.estudos.desafios.itau.entity.Client;
import com.estudos.desafios.itau.entity.dto.ClientDTO;
import com.estudos.desafios.itau.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSaveClient_Success() throws Exception {
        ClientDTO clientDTO = new ClientDTO("John", "Doe", BigDecimal.valueOf(50));
        Client client = new Client("John", "Doe", BigDecimal.valueOf(50));

        when(clientService.saveClient(any(ClientDTO.class))).thenReturn(client);

        mockMvc.perform(post("/client/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.participation").value(BigDecimal.valueOf(50)));
    }

    @Test
    void testSaveClient_MissingField() throws Exception {
        ClientDTO clientDTO = new ClientDTO(null, "Doe", BigDecimal.valueOf(50));

        mockMvc.perform(post("/client/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testListAllClients_Success() throws Exception {
        Client client1 = new Client("John", "Doe", BigDecimal.valueOf(50));
        Client client2 = new Client("Jane", "Doe", BigDecimal.valueOf(50));
        List<Client> clients = Arrays.asList(client1, client2);

        when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(get("/client/listAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void testListAllClients_EmptyList() throws Exception {
        when(clientService.getAllClients()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/client/listAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
