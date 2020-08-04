package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomSummaryStatisticsTest {

    static CustomSummaryStatistics stats;

    @BeforeAll
    public static void setUp() throws IOException {
        Reader in = new FileReader("src/test/resources/SalesRecords.csv");
        Iterable<CSVRecord> iterable =
                CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreEmptyLines(true)
                        .withDelimiter(',')
                        .withTrim()
                        .parse(in);
        stats = StreamSupport
                .stream(iterable.spliterator(), true)
                .map(r -> r.toMap())
                .collect(CustomSummaryStatistics.newCollector());
    }

    @Test
    public void calculateAvgDaysBetweenOrderDateAndShipDate() {
        assertEquals(12, stats.getAvgDaysBetweenOrderDateAndShipDate());
    }

    @Test
    public void calculateYearWithHighestOrders() {
        assertEquals(2015, stats.getYearWithMaxOrders().get().getKey());
        assertEquals(1351, stats.getYearWithMaxOrders().get().getValue());
    }

    @Test
    public void calculateItemWiseProfits() {
        assertEquals(12, stats.getItemWiseProfits().size());
        assertEquals(new BigDecimal("267551126.74"), stats.getItemWiseProfits().get("Vegetables"));
    }
}
