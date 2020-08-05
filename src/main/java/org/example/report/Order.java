package org.example.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @NotBlank(message = "Name cannot be blank")
    private String itemType;
    private LocalDate orderDate;
    private LocalDate shipDate;
    private BigDecimal totalProfit;
}
