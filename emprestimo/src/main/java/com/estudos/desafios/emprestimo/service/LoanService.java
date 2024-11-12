package com.estudos.desafios.emprestimo.service;

import com.estudos.desafios.emprestimo.controller.dto.CustomerLoanRequest;
import com.estudos.desafios.emprestimo.controller.dto.CustumerLoanResponse;
import com.estudos.desafios.emprestimo.controller.dto.LoanResponse;
import com.estudos.desafios.emprestimo.domain.Loan;
import com.estudos.desafios.emprestimo.domain.LoanType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    public CustumerLoanResponse checkLoanAvailability(CustomerLoanRequest loanRequest){

        var customer = loanRequest.toCustomer();
        var loan = new Loan(customer);

        List<LoanResponse> loans = new ArrayList<>();

        if (loan.isPersonalLoanAvailable()){
            loans.add(new LoanResponse(LoanType.PERSONAL, loan.getPersonalLoanInterestRate()));
        }

        if (loan.isConsigmentLoanAvailable()){
            loans.add(new LoanResponse(LoanType.CONSIGMENT, loan.getConsigmentLoanInterestRate()));
        }

        if (loan.isGuaranteedLoanAvailable()){
            loans.add(new LoanResponse(LoanType.GUARANTEED, loan.getGuaranteedLoanInterestRate()));
        }
        return new CustumerLoanResponse(loanRequest.name(), loans);
    }
}
