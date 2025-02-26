package ru.fil.moneyFlow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fil.moneyFlow.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
