package com.estudos.desafios.emprestimo.controller.dto;

import com.estudos.desafios.emprestimo.domain.LoanType;

public record LoanResponse(LoanType type, Double interestRate) {
}
