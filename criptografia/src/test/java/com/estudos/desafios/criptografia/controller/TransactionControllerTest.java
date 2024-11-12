package com.estudos.desafios.criptografia.controller;

import com.estudos.desafios.criptografia.controller.dto.CreateTransactionRequest;
import com.estudos.desafios.criptografia.controller.dto.TransactionResponse;
import com.estudos.desafios.criptografia.controller.dto.UpdateTransactionRequest;
import com.estudos.desafios.criptografia.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreate() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest("3cccc",
                "3cccc",
                300L);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service, times(1)).create(any(CreateTransactionRequest.class));
    }

    @Test
    public void testListAll() throws Exception {
        TransactionResponse response = new TransactionResponse(
                1L, // id
                "3cccc", // creditCardToken
                "3cccc", // userDocument
                1000L // transactionValue ou outro campo Long esperado
        );
        Page<TransactionResponse> pageResponse = new PageImpl<>(List.of(response));

        when(service.listAll(anyInt(), anyInt())).thenReturn(pageResponse);

        mockMvc.perform(get("/transactions")
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1));

        verify(service, times(1)).listAll(anyInt(), anyInt());
    }

    @Test
    public void testFindById() throws Exception {
        TransactionResponse response = new TransactionResponse(
                1L, // id
                "3cccc", // creditCardToken
                "3cccc", // userDocument
                1000L // transactionValue ou outro campo Long esperado
        );

        when(service.findById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/transactions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.creditCardToken").value(response.creditCardToken()))
                .andExpect(jsonPath("$.userDocument").value(response.userDocument()));

        verify(service, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdate() throws Exception {
        UpdateTransactionRequest request = new UpdateTransactionRequest(1L);

        mockMvc.perform(put("/transactions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(service, times(1)).update(anyLong(), any(UpdateTransactionRequest.class));
    }

    @Test
    public void testDeleteById() throws Exception {
        mockMvc.perform(delete("/transactions/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteById(anyLong());
    }
}