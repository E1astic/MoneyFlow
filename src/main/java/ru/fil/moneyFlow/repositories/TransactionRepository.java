package ru.fil.moneyFlow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fil.moneyFlow.models.Transaction;
import ru.fil.moneyFlow.models.TransactionType;
import ru.fil.moneyFlow.models.User;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndCategory_transactionType(User user, TransactionType transactionType);
}
