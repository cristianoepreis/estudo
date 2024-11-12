package com.estudos.desafios.criptografia.repository;

import com.estudos.desafios.criptografia.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
