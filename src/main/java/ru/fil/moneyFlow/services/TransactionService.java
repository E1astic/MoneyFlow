package ru.fil.moneyFlow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fil.moneyFlow.models.Transaction;
import ru.fil.moneyFlow.models.TransactionType;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.repositories.TransactionRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getByUser(User user) {
        return transactionRepository.findByUser(user);
    }

    public List<Transaction> getByUserAndTransactionType(User user, TransactionType transactionType) {
        return transactionRepository.findByUserAndCategory_transactionType(user, transactionType);
    }

    @Transactional
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
