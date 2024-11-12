package com.estudos.desafio.tratamento_erros.business.service;

import com.estudos.desafio.tratamento_erros.apiv1.dto.ProductsDTO;
import com.estudos.desafio.tratamento_erros.business.converter.ProdutoConverter;
import com.estudos.desafio.tratamento_erros.infrastructure.client.FakeApiClient;
import com.estudos.desafio.tratamento_erros.infrastructure.configs.error.NotificacaoErro;
import com.estudos.desafio.tratamento_erros.infrastructure.exceptions.BusinessException;
import com.estudos.desafio.tratamento_erros.infrastructure.exceptions.ConflictException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class FakeApiService {

    private final FakeApiClient cliente;
    private final ProdutoConverter converter;
    private final ProdutoService produtoService;

    public FakeApiService(FakeApiClient cliente, ProdutoConverter converter, ProdutoService produtoService) {
        this.cliente = cliente;
        this.converter = converter;
        this.produtoService = produtoService;
    }


    @NotificacaoErro
    public List<ProductsDTO> buscaProdutos() {
        try {


            List<ProductsDTO> dto = cliente.buscaListaProdutos();
            dto.forEach(produto -> {
                        Boolean retorno = produtoService.existsPorNome(produto.getNome());
                        if (retorno.equals(false)) {
                            produtoService.salvaProdutos(converter.toEntity(produto));
                        } else {
                            throw new ConflictException("Produto já existente no banco de dados " + produto.getNome());
                        }
                    }

            );
            return produtoService.buscaTodosProdutos();
        }catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar e gravar produtos no banco de dados");
        }
    }
}