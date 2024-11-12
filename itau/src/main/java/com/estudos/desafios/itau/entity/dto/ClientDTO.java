package com.estudos.desafios.itau.entity.dto;

import java.math.BigDecimal;

public class ClientDTO {
    private String firstName;
    private String lastName;
    private BigDecimal participation;

    // Construtores, getters e setters

    public ClientDTO(String firstName, String lastName, BigDecimal participation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.participation = participation;
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
