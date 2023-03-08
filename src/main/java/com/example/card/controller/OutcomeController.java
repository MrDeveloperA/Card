package com.example.card.controller;

import com.example.card.entity.Card;
import com.example.card.entity.Income;
import com.example.card.entity.Outcome;
import com.example.card.payload.IncomeDto;
import com.example.card.payload.OutcomeDto;
import com.example.card.repository.CardRepository;
import com.example.card.repository.IncomeRepository;
import com.example.card.repository.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/outcome")
public class OutcomeController {
    @Autowired
    OutcomeRepository outcomeRepository;
    @Autowired
    CardRepository cardRepository;

    //    Create

    @PostMapping
    public HttpEntity<?> doOutcome(@RequestBody OutcomeDto outcomeDto) {

        Outcome outcome = new Outcome();

        Optional<Card> optionalFromCard = cardRepository.findById(outcomeDto.getFromCardId());
        if (!optionalFromCard.isPresent())
            return ResponseEntity.notFound().build();
        outcome.setFromCardId(optionalFromCard.get());

        Optional<Card> optionalToCard = cardRepository.findById(outcomeDto.getToCardId());
        if (!optionalToCard.isPresent())
            return ResponseEntity.notFound().build();
        outcome.setToCardId(optionalToCard.get());

        outcome.setAmount(outcomeDto.getAmount());
        outcome.setDate(new Date(System.currentTimeMillis()));
        if (cardRepository.findById(outcomeDto.getFromCardId()).get().getBalance() >=
                (outcome.getAmount() + (outcome.getAmount() / 100))) {
            outcome.setCommissionAmount(outcome.getAmount() / 100);
        } else {
            ResponseEntity.status(HttpStatus.valueOf("Not enough money for commission"));
        }

        cardRepository.findById(outcomeDto.getFromCardId()).get().setBalance(
                cardRepository.findById(outcomeDto.getFromCardId()).get().getBalance()
                        - outcome.getAmount() - (outcome.getAmount() / 100)
        );

        cardRepository.findById(outcomeDto.getToCardId()).get().setBalance(
                cardRepository.findById(outcomeDto.getToCardId()).get().getBalance()
                        + outcome.getAmount()
        );

        Outcome save = outcomeRepository.save(outcome);
        return ResponseEntity.ok(save);
    }

    //    Get
    @GetMapping
    public HttpEntity<?> getOutcome() {
        return ResponseEntity.ok(outcomeRepository.findAll());
    }

    //   get by id

    @GetMapping("/{id}")
    public HttpEntity<?> getOutcomeById(@PathVariable Integer id) {
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        return ResponseEntity.status(optionalOutcome.isPresent() ? 200 : 404).body(optionalOutcome.orElse(null));
    }

    //    Delete

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOutcome(@PathVariable Integer id) {
        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (!optionalOutcome.isPresent())
            return ResponseEntity.notFound().build();

        outcomeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
