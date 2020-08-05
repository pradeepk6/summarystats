package org.example.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @NotBlank
    private String itemType;
    @NotNull
    private LocalDate orderDate;
    @NotNull
    private LocalDate shipDate;
    @NotNull
    private BigDecimal totalProfit;
}
