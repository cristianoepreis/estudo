package com.estudos.desafios.dtoToEntity.business.dto.mapStruct;

import com.estudos.desafios.dtoToEntity.business.dto.UsuarioRequestDTO;
import com.estudos.desafios.dtoToEntity.business.dto.UsuarioResponseDTO;
import com.estudos.desafios.dtoToEntity.infrastructure.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    UsuarioEntity paraUsuarioEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO paraUsuarioResponseDTO(UsuarioEntity entity);

    List<UsuarioResponseDTO> paraListaUsuarioResponseDTO(List<UsuarioEntity> lista);

}
