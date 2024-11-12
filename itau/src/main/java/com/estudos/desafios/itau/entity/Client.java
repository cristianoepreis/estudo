package com.estudos.desafios.itau.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal participation;

    public Client() {

    }

    public Client( String firstName, String lastName, BigDecimal participation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.participation = participation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getParticipation() {
        return participation;
    }

    public void setParticipation(BigDecimal participation) {
        this.participation = participation;
    }
}
