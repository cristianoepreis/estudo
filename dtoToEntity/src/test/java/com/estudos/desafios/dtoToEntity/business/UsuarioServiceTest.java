package com.estudos.desafios.dtoToEntity.business;

import com.estudos.desafios.dtoToEntity.business.dto.UsuarioRequestDTO;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioResponseDTO;
import com.estudos.desafios.dtoToEntity.business.dto.mapStruct.UsuarioMapper;
import com.estudos.desafios.dtoToEntity.infrastructure.repository.UsuarioRepository;
import com.estudos.desafios.dtoToEntity.infrastructure.entity.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUsuario() {
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioMapper.paraUsuarioEntity(requestDTO)).thenReturn(usuarioEntity);
        when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
        when(usuarioMapper.paraUsuarioResponseDTO(usuarioEntity)).thenReturn(responseDTO);

        UsuarioResponseDTO result = usuarioService.saveUsuario(requestDTO);

        assertEquals(responseDTO, result);
        verify(usuarioMapper, times(1)).paraUsuarioEntity(requestDTO);
        verify(usuarioRepository, times(1)).save(usuarioEntity);
        verify(usuarioMapper, times(1)).paraUsuarioResponseDTO(usuarioEntity);
    }

    @Test
    void testFindAllUsuarios() {
        List<UsuarioEntity> usuarioEntities = Arrays.asList(new UsuarioEntity(), new UsuarioEntity());
        List<UsuarioResponseDTO> responseDTOs = Arrays.asList(new UsuarioResponseDTO(), new UsuarioResponseDTO());

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        when(usuarioMapper.paraListaUsuarioResponseDTO(usuarioEntities)).thenReturn(responseDTOs);

        List<UsuarioResponseDTO> result = usuarioService.findAllUsuarios();

        assertEquals(responseDTOs, result);
        verify(usuarioRepository, times(1)).findAll();
        verify(usuarioMapper, times(1)).paraListaUsuarioResponseDTO(usuarioEntities);
    }

    @Test
    void testFindUsuarioByEmail() {
        String email = "teste@example.com";
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findByEmail(email)).thenReturn(usuarioEntity);
        when(usuarioMapper.paraUsuarioResponseDTO(usuarioEntity)).thenReturn(responseDTO);

        UsuarioResponseDTO result = usuarioService.findUsuarioByEmail(email);

        assertEquals(responseDTO, result);
        verify(usuarioRepository, times(1)).findByEmail(email);
        verify(usuarioMapper, times(1)).paraUsuarioResponseDTO(usuarioEntity);
    }

    @Test
    void testDeleteUsuarioForEmail() {
        String email = "teste@example.com";

        doNothing().when(usuarioRepository).deleteByEmail(email);

        usuarioService.deleteUsuarioForEmail(email);

        verify(usuarioRepository, times(1)).deleteByEmail(email);
    }
}
