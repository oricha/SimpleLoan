package com.example.simpleloan.service;

import com.example.simpleloan.domain.LoanResponse;

public interface SimpleLoanService {

    void saveLenders(String csvFile);
    LoanResponse calculate(Double requestedAmount);
}
