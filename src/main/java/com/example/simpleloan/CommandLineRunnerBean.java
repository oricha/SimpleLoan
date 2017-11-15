package com.example.simpleloan;

import com.example.simpleloan.domain.LoanResponse;
import com.example.simpleloan.service.SimpleLoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommandLineRunnerBean implements CommandLineRunner {

    private static DecimalFormat dfRepayment = new DecimalFormat("#.##");
    private static DecimalFormat dfRate = new DecimalFormat("#.#");

    @Autowired
    private SimpleLoanService service;

    public void run(String... args) {

        log.info("Application started with arguments:" + Arrays.stream(args).collect(Collectors.joining("|")));

        String fileName = args[0];
        String requestedAmount = args[1];
        service.saveLenders( fileName);
        LoanResponse loanResponse = service.calculate( new Double(requestedAmount));

        if(null == loanResponse){
            System.out.println("It is not possible to provide a quote at that time.");
        }else {
            System.out.println("Requested Amount: £" + dfRepayment.format(new Double(requestedAmount)));
            System.out.println("Rate: " + dfRate.format(loanResponse.getRate())+"%");
            System.out.println("Monthly repayment: £" + dfRepayment.format(loanResponse.getRepayment()));
            System.out.println("Total Repayment: £" + dfRepayment.format(loanResponse.getTotalRepayment()));
        }
        log.info("Exit");
    }

}
