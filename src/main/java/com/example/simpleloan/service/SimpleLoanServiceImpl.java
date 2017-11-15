package com.example.simpleloan.service;

import com.example.simpleloan.Repository.LenderRepository;
import com.example.simpleloan.domain.Lender;
import com.example.simpleloan.domain.LoanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class SimpleLoanServiceImpl implements  SimpleLoanService{

    private static int MONTHS = 36;
    @Autowired
    private LenderRepository repository;
    public void saveLenders(String csvFile) {

        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null)  // To escape first line
                while ((line = br.readLine()) != null) {
                    String[] lender = line.split(cvsSplitBy);
                    repository.save(new Lender(lender[0], new Double(lender[1]),new Double(lender[2])));
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("Completed reading file. Lenders saved");
    }

    @Override
    public LoanResponse calculate(Double requestedAmount) {

        LoanResponse result = new LoanResponse();
        Double collect = new Double(0);

        // Sort lenders by rate
        List<Lender> lenders = repository.findAllByOrderByRate();
        Double highestRate = new Double(0);
        boolean satisfiedLoan = false;
        Iterator iterator = lenders.listIterator();
        while( !satisfiedLoan && iterator.hasNext()){
            Lender lender = (Lender)iterator.next();
            collect+= lender.getAvailable();
            // As in the document it is not clear what is a "competitive quotes", I will take the highest rate of the selected lenders
            highestRate = round(lender.getRate(), 2);
            if( collect >= requestedAmount) {
                satisfiedLoan = true;
            }
        }
        if(!satisfiedLoan){
            return null;
        }
        //Total Capital =  Initial capital * ( 1 + interest)^Time
        Double totalRepayment = requestedAmount * Math.pow(( 1 + highestRate/100), MONTHS) ;
        result.setRate(highestRate * 100);
        result.setRepayment( totalRepayment /36);
        result.setTotalRepayment(totalRepayment);
        return result;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
