package com.estudos.desafios.emprestimo.controller.dto;

import java.util.List;

public record CustumerLoanResponse(String customer, List<LoanResponse> loans) {
}
