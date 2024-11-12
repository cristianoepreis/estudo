package com.estudos.desafios.itau.repository;

import com.estudos.desafios.itau.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
