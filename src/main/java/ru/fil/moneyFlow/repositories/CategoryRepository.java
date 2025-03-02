package ru.fil.moneyFlow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fil.moneyFlow.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
