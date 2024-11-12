package com.estudos.desafios.emprestimo.controller;

import com.estudos.desafios.emprestimo.controller.dto.CustomerLoanRequest;
import com.estudos.desafios.emprestimo.controller.dto.CustumerLoanResponse;
import com.estudos.desafios.emprestimo.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(value = "customer-loans")
    public ResponseEntity<CustumerLoanResponse> customerLoans(@RequestBody @Valid CustomerLoanRequest loanRequest){

        var loanResponse = loanService.checkLoanAvailability(loanRequest);

        return ResponseEntity.ok(loanResponse);
    }
}
