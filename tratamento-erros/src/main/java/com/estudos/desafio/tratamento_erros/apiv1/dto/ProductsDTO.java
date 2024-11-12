package com.estudos.desafio.tratamento_erros.apiv1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Objects;

public class ProductsDTO {

    @JsonProperty(value = "id")
    @JsonIgnore
    private Long id;
    @JsonProperty(value = "entity_id")
    private String entityId;
    @JsonProperty(value = "title")
    private String nome;
    @JsonProperty(value = "price")
    private BigDecimal preco;
    @JsonProperty(value = "category")
    private String categoria;
    @JsonProperty(value = "description")
    private String descricao;
    @JsonProperty(value = "image")
    private String imagem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsDTO that = (ProductsDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(entityId, that.entityId) && Objects.equals(nome, that.nome) && Objects.equals(preco, that.preco) && Objects.equals(categoria, that.categoria) && Objects.equals(descricao, that.descricao) && Objects.equals(imagem, that.imagem);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(entityId);
        result = 31 * result + Objects.hashCode(nome);
        result = 31 * result + Objects.hashCode(preco);
        result = 31 * result + Objects.hashCode(categoria);
        result = 31 * result + Objects.hashCode(descricao);
        result = 31 * result + Objects.hashCode(imagem);
        return result;
    }
}