package ru.fil.moneyFlow.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fil.moneyFlow.dto.TransactionRequest;
import ru.fil.moneyFlow.models.Category;
import ru.fil.moneyFlow.models.Transaction;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.services.CategoryService;
import ru.fil.moneyFlow.services.TransactionService;
import ru.fil.moneyFlow.services.UserService;
import ru.fil.moneyFlow.utils.CompositeExceptionResponse;
import ru.fil.moneyFlow.utils.CompositeExceptionResponseGenerator;

@RestController
@RequestMapping("/transaction")
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
        Transaction t=convertToTransaction(transactionRequest);
        transactionService.save(t);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private Transaction convertToTransaction(TransactionRequest transactionRequest) {
        Category category=categoryService.getCategory(transactionRequest.getCategoryId()).get();
        //System.out.println(category);
        User userFromContext =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=userService.getById(userFromContext.getId()).get();
        //System.out.println(user);

        Transaction transaction=new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());

        //System.out.println(transaction);
        transaction.setCategory(category);
        //System.out.println(transaction);
        transaction.setUser(user);
        //System.out.println(transaction);

        category.addTransaction(transaction);
        //System.out.println(category);
        user.addTransaction(transaction);
        //System.out.println(user);

        return transaction;
    }
}
