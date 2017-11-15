package com.example.simpleloan.Repository;

import com.example.simpleloan.domain.Lender;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface LenderRepository extends CrudRepository<Lender, UUID> {

    List<Lender> findAllByOrderByRate();
}
