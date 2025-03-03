package ru.fil.moneyFlow.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fil.moneyFlow.dto.TransactionRequest;
import ru.fil.moneyFlow.dto.TransactionResponse;
import ru.fil.moneyFlow.models.Category;
import ru.fil.moneyFlow.models.Transaction;
import ru.fil.moneyFlow.models.TransactionType;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.services.CategoryService;
import ru.fil.moneyFlow.services.TransactionService;
import ru.fil.moneyFlow.services.UserService;
import ru.fil.moneyFlow.utils.CompositeExceptionResponse;
import ru.fil.moneyFlow.utils.CompositeExceptionResponseGenerator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final ModelMapper modelMapper;
    private final TransactionService transactionService;
    private final UserService userService;
    private final CategoryService categoryService;

    public TransactionController(ModelMapper modelMapper, TransactionService transactionService, UserService userService, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.transactionService = transactionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(
            @RequestBody @Valid TransactionRequest transactionRequest,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            CompositeExceptionResponse compositeExceptionResponse
                    = CompositeExceptionResponseGenerator.generate(bindingResult);
            return new ResponseEntity<>(compositeExceptionResponse, HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = convertToTransaction(transactionRequest);
        transaction=transactionService.save(transaction);
        return ResponseEntity.ok(Map.of("transactionId", transaction.getId()));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        User user=getCurrentUser();
        List<Transaction> transactions=transactionService.getByUser(user);
        List<TransactionResponse> transactionResponses=transactions.stream()
                .map(this::convertToTransactionResponse).collect(Collectors.toList());
        return ResponseEntity.ok(transactionResponses);
    }

    @GetMapping("/income")
    public ResponseEntity<List<TransactionResponse>> getIncomeTransactions() {
        User user=getCurrentUser();
        List<Transaction> transactions=transactionService.getByUserAndTransactionType(user, TransactionType.INCOME);
        List<TransactionResponse> transactionResponses=transactions.stream()
                .map(this::convertToTransactionResponse).collect(Collectors.toList());
        return ResponseEntity.ok(transactionResponses);
    }

    @GetMapping("/expense")
    public ResponseEntity<List<TransactionResponse>> getExpenseTransactions() {
        User user=getCurrentUser();
        List<Transaction> transactions=transactionService.getByUserAndTransactionType(user, TransactionType.EXPENSE);
        List<TransactionResponse> transactionResponses=transactions.stream()
                .map(this::convertToTransactionResponse).collect(Collectors.toList());
        return ResponseEntity.ok(transactionResponses);
    }


    private User getCurrentUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Transaction convertToTransaction(TransactionRequest transactionRequest) {
        Category category=categoryService.getById(transactionRequest.getCategoryId()).get();
        User userFromContext=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userService.getById(userFromContext.getId()).get();

        Transaction transaction=new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());

        transaction.setCategory(category);
        transaction.setUser(user);

        category.addTransaction(transaction);
        user.addTransaction(transaction);

        return transaction;
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponse.class);
    }
}
