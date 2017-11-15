package com.example.simpleloan.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {

    private Double rate;
    private Double repayment;
    private Double totalRepayment;
}
