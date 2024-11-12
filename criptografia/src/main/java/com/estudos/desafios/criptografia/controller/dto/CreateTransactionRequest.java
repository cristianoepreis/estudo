package com.estudos.desafios.criptografia.controller.dto;

public record CreateTransactionRequest(String userDocument,
                                       String creditCardToken,
                                       Long value) {
}
