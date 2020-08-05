package org.example.report;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String itemType;
    private LocalDate orderDate;
    private LocalDate shipDate;
    private BigDecimal totalProfit;
}
