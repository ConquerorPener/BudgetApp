package com.example.pasir_wojciak_radoslaw.controller;

import com.example.pasir_wojciak_radoslaw.DTO.BalanceDTO;
import com.example.pasir_wojciak_radoslaw.DTO.TransactionDTO;
import com.example.pasir_wojciak_radoslaw.Service.TransactionService;
import com.example.pasir_wojciak_radoslaw.model.Transaction;
import com.example.pasir_wojciak_radoslaw.model.TransactionType;
import com.example.pasir_wojciak_radoslaw.model.User;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Validated
public class TransactionGraphQLController {
    private final TransactionService transactionService;

    public TransactionGraphQLController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @QueryMapping(name = "transactions")
    public List<Transaction> getTransactions() {
        return transactionService.getAllTransactions();
    }

    @MutationMapping
    public Transaction addTransaction(
            @Valid @Argument TransactionDTO transactionDTO
    )
    {
        return transactionService.createTransaction(transactionDTO);
    }

    @MutationMapping
    public Transaction updateTransaction(
            @Argument Long id,
            @Valid @Argument TransactionDTO transactionDTO
    ) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @MutationMapping
    public Boolean deleteTransaction(@Argument Long id) {
        try {
            transactionService.deleteTransaction(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @QueryMapping
    public BalanceDTO userBalance(@Argument Float days) {
        User user = transactionService.getCurrentUser();
        return transactionService.getUserBalance(user, days);
    }
}

