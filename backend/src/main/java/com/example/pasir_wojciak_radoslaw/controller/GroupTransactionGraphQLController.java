package com.example.pasir_wojciak_radoslaw.controller;

import com.example.pasir_wojciak_radoslaw.DTO.GroupTransactionDTO;
import com.example.pasir_wojciak_radoslaw.Service.GroupTransactionService;
import com.example.pasir_wojciak_radoslaw.Service.TransactionService;
import com.example.pasir_wojciak_radoslaw.model.User;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GroupTransactionGraphQLController {
    private final GroupTransactionService groupTransactionService;
    private final TransactionService transactionService;

    public GroupTransactionGraphQLController(GroupTransactionService groupTransactionService, TransactionService transactionService) {
        this.groupTransactionService = groupTransactionService;
        this.transactionService = transactionService;
    }

    @MutationMapping
    public Boolean addGroupTransaction(@Argument GroupTransactionDTO groupTransactionDTO) {
        User user = transactionService.getCurrentUser();
        groupTransactionService.addGroupTransaction(groupTransactionDTO, user);
        return true;
    }
}
