package ru.fil.moneyFlow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Transaction type should not be empty")
    private TransactionType transactionType;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;


    public void addTransaction(Transaction transaction) {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }
}
