package com.example.simpleloan.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="LENDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Double rate;
    private Double available;

    public Lender( String name, Double rate, Double available){
        super();
        this.name = name;
        this.rate = rate;
        this.available = available;
    }
}
