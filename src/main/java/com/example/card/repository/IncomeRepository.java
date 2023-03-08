package com.example.card.repository;

import com.example.card.entity.Card;
import com.example.card.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
