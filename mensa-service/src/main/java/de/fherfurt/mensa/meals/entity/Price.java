package de.fherfurt.mensa.meals.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
public class Price {
    private PriceTypes typeOfPrice;
    private BigDecimal amount;
    private Currency currency;
}
