package org.example;

import org.example.report.CustomSummaryStatistics;
import org.example.report.SalesReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CustomSummaryStatisticsTest {

    static CustomSummaryStatistics stats;

    @BeforeAll
    public static void setUp() throws IOException {
        stats = SalesReport.calculateSummaryStats("src/test/resources/SalesRecord-test.csv");
    }

    @Test
    public void calculateAvgDaysBetweenOrderDateAndShipDate() {
        assertEquals(15, stats.getAvgDaysBetweenOrderDateAndShipDate());
    }

    @Test
    public void calculateYearWithHighestOrders() {
        assertEquals(2015, stats.getYearWithMaxOrders().get().getKey());
        assertEquals(3, stats.getYearWithMaxOrders().get().getValue());
    }

    @Test
    public void calculateItemWiseProfits() {
        assertEquals(6, stats.getItemWiseProfits().size());
        assertEquals(new BigDecimal("411291.95"), stats.getItemWiseProfits().get("Vegetables"));
    }

    @Test
    public void badRowShouldHaveBeenExcluded() {
        assertFalse(stats.getItemWiseProfits().containsKey("badrow"));
        assertEquals(9, stats.getNumRecords());
    }


}
