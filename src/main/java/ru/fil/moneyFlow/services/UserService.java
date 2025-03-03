package ru.fil.moneyFlow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fil.moneyFlow.dto.UserRequest;
import ru.fil.moneyFlow.dto.UserResponse;
import ru.fil.moneyFlow.models.User;
import ru.fil.moneyFlow.repositories.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User update(int id, UserRequest userRequest){
        User user=userRepository.findById(id).get();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setPassword(userRequest.getPassword());
        return user;
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
