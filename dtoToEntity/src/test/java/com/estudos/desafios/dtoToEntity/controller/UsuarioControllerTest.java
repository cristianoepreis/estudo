package com.estudos.desafios.dtoToEntity.controller;

import com.estudos.desafios.dtoToEntity.business.UsuarioService;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioRequestDTO;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUsuario() {
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioService.saveUsuario(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.save(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(usuarioService, times(1)).saveUsuario(requestDTO);
    }

    @Test
    void testFindByEmail() {
        String email = "teste@example.com";
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioService.findUsuarioByEmail(email)).thenReturn(responseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.findByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(usuarioService, times(1)).findUsuarioByEmail(email);
    }

    @Test
    void testFindAllUsuarios() {
        List<UsuarioResponseDTO> responseDTOList = Arrays.asList(new UsuarioResponseDTO(), new UsuarioResponseDTO());

        when(usuarioService.findAllUsuarios()).thenReturn(responseDTOList);

        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.findAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTOList, response.getBody());
        verify(usuarioService, times(1)).findAllUsuarios();
    }

    @Test
    void testDeleteUsuario() {
        String email = "teste@example.com";

        doNothing().when(usuarioService).deleteUsuarioForEmail(email);

        ResponseEntity<Void> response = usuarioController.deleteUsuario(email);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).deleteUsuarioForEmail(email);
    }
}
