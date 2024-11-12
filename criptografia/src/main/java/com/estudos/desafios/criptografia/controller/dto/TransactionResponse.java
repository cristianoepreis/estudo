package com.estudos.desafios.criptografia.controller.dto;

import com.estudos.desafios.criptografia.entity.TransactionEntity;

public record TransactionResponse(Long id,
                                  String userDocument,
                                  String creditCardToken,
                                  Long valeu) {

    public static TransactionResponse fromEntity(TransactionEntity entity) {
        return new TransactionResponse(
                entity.getTransationId(),
                entity.getRawUserDocument(),
                entity.getRawCreditCardToken(),
                entity.getTransactionValue()
        );
    }
}
