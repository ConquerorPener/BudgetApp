package com.example.pasir_wojciak_radoslaw.Repository;

import com.example.pasir_wojciak_radoslaw.model.Transaction;
import com.example.pasir_wojciak_radoslaw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);

}
