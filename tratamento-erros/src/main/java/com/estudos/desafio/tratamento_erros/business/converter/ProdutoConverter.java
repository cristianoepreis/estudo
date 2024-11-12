package com.estudos.desafio.tratamento_erros.business.converter;

import com.estudos.desafio.tratamento_erros.apiv1.dto.ProductsDTO;
import com.estudos.desafio.tratamento_erros.infrastructure.entities.ProdutoEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class ProdutoConverter {

    public ProdutoEntity toEntity(ProductsDTO dto) {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(String.valueOf(UUID.randomUUID()));
        entity.setNome(dto.getNome());
        entity.setCategoria(dto.getCategoria());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setImagem(dto.getImagem());
        entity.setDataInclusao(LocalDateTime.now());
        return entity;
    }


    public ProdutoEntity toEntityUpdate(ProdutoEntity entity, ProductsDTO dto, String id) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        entity.setId(id);
        entity.setNome(dto.getNome() != null ? dto.getNome() : entity.getNome());
        entity.setCategoria(dto.getCategoria() != null ? dto.getCategoria() : entity.getCategoria());
        entity.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : entity.getDescricao());
        entity.setPreco(dto.getPreco() != null ? dto.getPreco() : entity.getPreco());
        entity.setImagem(dto.getImagem() != null ? dto.getImagem() : entity.getImagem());
        entity.setDataAtualizacao(LocalDateTime.now());

        return entity;
    }


    public ProductsDTO toDTO(ProdutoEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        ProductsDTO dto = new ProductsDTO();
        dto.setEntityId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCategoria(entity.getCategoria());
        dto.setDescricao(entity.getDescricao());
        dto.setPreco(entity.getPreco());
        dto.setImagem(entity.getImagem());

        return dto;
    }


    public List<ProductsDTO> toListDTO(List<ProdutoEntity> entityList) {
        return entityList.stream().map(this::toDTO).toList();
    }
}