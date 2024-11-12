package com.estudos.desafio.tratamento_erros.infrastructure.message.consumer;

import com.estudos.desafio.tratamento_erros.business.service.ProdutoService;
import org.springframework.stereotype.Component;

@Component
public class FakeApiConsumer {

    private final ProdutoService produtoService;

    public FakeApiConsumer(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

//    @KafkaListener(topics = "${topico.fake-api.consumer.nome}", groupId = "${topico.fake-api.consumer.group-id}")
//    public void recebeProdutosDTO(ProductsDTO productsDTO) {
//        try{
//            produtoService.salvaProdutoConsumer(productsDTO);
//        } catch (Exception exception) {
//            throw new BusinessException("Erro ao consumir mensagem do kafka ");
//        }
//    }
}
