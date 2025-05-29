package com.example.pasir_wojciak_radoslaw.Service;


import com.example.pasir_wojciak_radoslaw.DTO.BalanceDTO;
import com.example.pasir_wojciak_radoslaw.DTO.TransactionDTO;
import com.example.pasir_wojciak_radoslaw.Repository.UserRepository;
import com.example.pasir_wojciak_radoslaw.model.Transaction;
import com.example.pasir_wojciak_radoslaw.Repository.TransactionRepository;
import com.example.pasir_wojciak_radoslaw.model.TransactionType;
import com.example.pasir_wojciak_radoslaw.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> getAllTransactions() {
        User user = getCurrentUser();
        return transactionRepository.findAllByUser(user);
    }

    public Transaction saveTransactions(TransactionDTO transactionDetails){

        Transaction transaction = new Transaction(
                transactionDetails.getAmount(),
                transactionDetails.getType(),
                transactionDetails.getTags(),
                transactionDetails.getNotes(),
                getCurrentUser(),
                LocalDateTime.now()
        );

        return transactionRepository.save(transaction);
    }


    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono transakcji o ID " + id));
    }

    public Transaction updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono transakcji o ID " + id));

        if(!transaction.getUser().getEmail().equals(getCurrentUser().getEmail()))
        {
            throw new SecurityException("Brak dostepu do edycji tej transakcji");
        }

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(transactionDTO.getType());
        transaction.setTags(transactionDTO.getTags());
        transaction.setNotes(transactionDTO.getNotes());

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTags(transactionDTO.getTags());
        transaction.setType(transactionDTO.getType());
        transaction.setNotes(transactionDTO.getNotes());
        transaction.setUser(getCurrentUser());
        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono zalogowanego użytkownika"));
    }


    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono transakcji o ID " + id));
        transactionRepository.delete(transaction);
    }

    public BalanceDTO getUserBalance(User user, Float days) {
        List<Transaction> userTransactions = transactionRepository.findAllByUser(user);

        // Jeśli określono liczbę dni, filtruj transakcje według daty
        if (days != null && days > 0) {
            LocalDateTime startDate = LocalDateTime.now().minusDays(days.longValue());
            userTransactions = userTransactions.stream()
                    .filter(transaction -> transaction.getTimestamp() != null &&
                            transaction.getTimestamp().isAfter(startDate))
                    .collect(Collectors.toList());
        }

        double income = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return new BalanceDTO(income, expense, income - expense);
    }



}