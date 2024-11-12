package com.estudos.desafios.criptografia.entity;

import com.estudos.desafios.criptografia.service.CryptoService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_transations")
public class TransactionEntity {

    @Id
    @Column(name = "transation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transationId;

    @Column(name = "user_document")
    private String encryptedUserDocument;

    @Column(name = "credit_card_token")
    private String encryptedCreditCardToken;

    @Column(name = "transaction_value")
    private Long transactionValue;

    @Transient
    private String rawUserDocument;

    @Transient
    private String rawCreditCardToken;

    public TransactionEntity() {
    }

    public Long getTransationId() {
        return transationId;
    }

    public void setTransationId(Long transationId) {
        this.transationId = transationId;
    }

    public String getEncryptedUserDocument() {
        return encryptedUserDocument;
    }

    public void setEncryptedUserDocument(String encryptedUserDocument) {
        this.encryptedUserDocument = encryptedUserDocument;
    }

    public String getEncryptedCreditCardToken() {
        return encryptedCreditCardToken;
    }

    public void setEncryptedCreditCardToken(String encryptedCreditCardToken) {
        this.encryptedCreditCardToken = encryptedCreditCardToken;
    }

    public Long getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(Long transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getRawUserDocument() {
        return rawUserDocument;
    }

    public void setRawUserDocument(String rawUserDocument) {
        this.rawUserDocument = rawUserDocument;
    }

    public String getRawCreditCardToken() {
        return rawCreditCardToken;
    }

    public void setRawCreditCardToken(String rawCreditCardToken) {
        this.rawCreditCardToken = rawCreditCardToken;
    }

    @PrePersist
    public void prePersist() {
        this.encryptedUserDocument = CryptoService.encrypt(this.rawUserDocument);
        this.encryptedCreditCardToken = CryptoService.encrypt(this.rawCreditCardToken);
    }

    @PostLoad
    public void postLoad() {
        this.rawUserDocument = CryptoService.decrypt(this.encryptedUserDocument);
        this.rawCreditCardToken = CryptoService.decrypt(this.encryptedCreditCardToken);
    }
}
