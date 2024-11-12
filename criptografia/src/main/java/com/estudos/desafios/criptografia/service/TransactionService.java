package com.estudos.desafios.criptografia.service;

import com.estudos.desafios.criptografia.controller.dto.CreateTransactionRequest;
import com.estudos.desafios.criptografia.controller.dto.TransactionResponse;
import com.estudos.desafios.criptografia.controller.dto.UpdateTransactionRequest;
import com.estudos.desafios.criptografia.entity.TransactionEntity;
import com.estudos.desafios.criptografia.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void create(CreateTransactionRequest request){

        var entity = new TransactionEntity();
        entity.setRawCreditCardToken(request.creditCardToken());
        entity.setRawUserDocument(request.userDocument());
        entity.setTransactionValue(request.value());

        repository.save(entity);
    }

    public TransactionResponse findById(Long id){
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return TransactionResponse.fromEntity(entity);
    }

    public Page<TransactionResponse> listAll(int page, int size){
        var content = repository.findAll(PageRequest.of(page, size));
        return content.map(TransactionResponse::fromEntity);
    }

    public void update(Long id, UpdateTransactionRequest request) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        entity.setTransactionValue(request.value());

        repository.save(entity);
    }

    public void deleteById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.deleteById(entity.getTransationId());
    }
}
