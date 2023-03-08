package com.example.card.payload;

import com.example.card.entity.Card;
import lombok.Data;

import javax.persistence.OneToOne;
import java.sql.Date;

@Data
public class IncomeDto {
    private Integer fromCardId;
    private Integer toCardId;
    private double amount;
    private Date date;
}
