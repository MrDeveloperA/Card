package com.example.card.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class OutcomeDto {
    private Integer fromCardId;
    private Integer toCardId;
    private double amount;
    private Date date;
    private double commissionAmount;
}
