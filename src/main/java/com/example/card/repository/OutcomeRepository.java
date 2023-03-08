package com.example.card.repository;

import com.example.card.entity.Income;
import com.example.card.entity.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

}
