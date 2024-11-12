package com.estudos.desafios.dtoToEntity.business;

import com.estudos.desafios.dtoToEntity.business.dto.UsuarioRequestDTO;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioResponseDTO;
import com.estudos.desafios.dtoToEntity.business.dto.mapStruct.UsuarioMapper;
import com.estudos.desafios.dtoToEntity.infrastructure.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO request) {
        return usuarioMapper.paraUsuarioResponseDTO(
                usuarioRepository.save(
                        usuarioMapper.paraUsuarioEntity(request)));
    }

    public List<UsuarioResponseDTO> findAllUsuarios(){
        return usuarioMapper.paraListaUsuarioResponseDTO(
                usuarioRepository.findAll());
    }

    public UsuarioResponseDTO findUsuarioByEmail(String email){
        return usuarioMapper.paraUsuarioResponseDTO(
                usuarioRepository.findByEmail(email));
    }

    public void deleteUsuarioForEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

}
