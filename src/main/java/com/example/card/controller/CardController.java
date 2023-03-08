package com.example.card.controller;

import com.example.card.entity.Card;
import com.example.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @Autowired
    CardRepository cardRepository;

    //    Create

    @PostMapping
    public HttpEntity<?> addCard(@RequestBody Card card) {

        Card card1 = new Card();
        card1.setUsername(card.getUsername());
        card1.setNumber(card.getNumber());
        card1.setBalance(card.getBalance());
        card1.setExpiredDate(card.getExpiredDate());
        card1.setActive(card.isActive());

        Card save = cardRepository.save(card1);
        return ResponseEntity.ok(save);
    }

    //    Get
    @GetMapping
    public HttpEntity<?> getCard() {
        return ResponseEntity.ok(cardRepository.findAll());
    }

    //   get by id

    @GetMapping("/{id}")
    public HttpEntity<?> getCardById(@PathVariable Integer id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return ResponseEntity.status(optionalCard.isPresent() ? 200 : 404).body(optionalCard.orElse(null));
    }

    //    Update
    @PutMapping("/{id}")
    public HttpEntity<?> editCard(@PathVariable Integer id, @RequestBody Card card) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent())
            return ResponseEntity.notFound().build();

        Card editCard = optionalCard.get();
        editCard.setUsername(card.getUsername());
        editCard.setNumber(card.getNumber());
        editCard.setBalance(card.getBalance());
        editCard.setExpiredDate(card.getExpiredDate());
        editCard.setActive(card.isActive());

        Card save = cardRepository.save(editCard);
        return ResponseEntity.ok(save);

    }

    //    Delete

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCard(@PathVariable Integer id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent())
            return ResponseEntity.notFound().build();

        cardRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
