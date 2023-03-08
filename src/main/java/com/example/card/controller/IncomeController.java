package com.example.card.controller;

import com.example.card.entity.Card;
import com.example.card.entity.Income;
import com.example.card.payload.IncomeDto;
import com.example.card.repository.CardRepository;
import com.example.card.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    @Autowired
    IncomeRepository incomeRepository;
    @Autowired
    CardRepository cardRepository;

    //    Create

    @PostMapping
    public HttpEntity<?> doIncome(@RequestBody IncomeDto incomeDto) {

        Income income = new Income();

        Optional<Card> optionalFromCard = cardRepository.findById(incomeDto.getFromCardId());
        if (!optionalFromCard.isPresent())
            return ResponseEntity.notFound().build();
        income.setFromCardId(optionalFromCard.get());

        Optional<Card> optionalToCard = cardRepository.findById(incomeDto.getToCardId());
        if (!optionalToCard.isPresent())
            return ResponseEntity.notFound().build();
        income.setToCardId(optionalToCard.get());

        income.setAmount(incomeDto.getAmount());
        income.setDate(new Date(System.currentTimeMillis()));

        cardRepository.findById(incomeDto.getFromCardId()).get().setBalance(
                cardRepository.findById(incomeDto.getFromCardId()).get().getBalance()
                        - income.getAmount()
        );

        cardRepository.findById(incomeDto.getToCardId()).get().setBalance(
                cardRepository.findById(incomeDto.getToCardId()).get().getBalance()
                        + income.getAmount()
        );

        Income save = incomeRepository.save(income);
        return ResponseEntity.ok(save);
    }

    //    Get
    @GetMapping
    public HttpEntity<?> getIncome() {
        return ResponseEntity.ok(incomeRepository.findAll());
    }

    //   get by id

    @GetMapping("/{id}")
    public HttpEntity<?> getIncomeById(@PathVariable Integer id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        return ResponseEntity.status(optionalIncome.isPresent() ? 200 : 404).body(optionalIncome.orElse(null));
    }

    //    Delete

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteIncome(@PathVariable Integer id) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (!optionalIncome.isPresent())
            return ResponseEntity.notFound().build();

        incomeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
