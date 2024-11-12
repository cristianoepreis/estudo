package com.estudos.desafios.senha_segura.controller;

import com.estudos.desafios.senha_segura.controller.ApiController;
import com.estudos.desafios.senha_segura.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        // Configurar o comportamento do PasswordService para o teste
        when(passwordService.validatePass(anyString())).thenReturn(List.of());
    }

    @Test
    void testValidatePassword_ValidPassword() throws Exception {
        String validPasswordRequest = "{ \"password\": \"Abcdef1@\" }";

        mockMvc.perform(post("/validate-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validPasswordRequest))
                .andExpect(status().isNoContent()); // Espera 204 quando a senha é válida
    }

    @Test
    void testValidatePassword_InvalidPassword() throws Exception {
        String invalidPasswordRequest = "{ \"password\": \"abc\" }";
        List<String> expectedFailures = List.of("A senha deve ter no mínimo 8 caracteres.");

        when(passwordService.validatePass(anyString())).thenReturn(expectedFailures);

        mockMvc.perform(post("/validate-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPasswordRequest))
                .andExpect(status().isBadRequest()) // Espera 400 quando a senha é inválida
                .andExpect(jsonPath("$.failures").isArray())
                .andExpect(jsonPath("$.failures[0]").value("A senha deve ter no mínimo 8 caracteres."));
    }
}
