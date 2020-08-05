package org.example.report;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.StreamSupport;

public class SalesReport {

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/y");
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CustomSummaryStatistics stats;
        try {
            stats = calculateSummaryStats("data/SalesRecords.csv");
            System.out.println(stats.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Time(milli-seconds) taken to generate Sales Report : " + (System.currentTimeMillis() - start));
    }

    public static CustomSummaryStatistics calculateSummaryStats(String filePath) throws IOException {
        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> iterable =
                CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreEmptyLines(true)
                        .withDelimiter(',')
                        .withTrim()
                        .parse(in);
        return StreamSupport
                .stream(iterable.spliterator(), true)
                .map(csvRecord -> toOrder(csvRecord))
                .filter(order -> order != null)
                .collect(CustomSummaryStatistics.newCollector());
    }

    // map and validate
    public static Order toOrder(CSVRecord record) {
        Order order = new Order();
        try {
            order.setItemType(record.get("Item Type"));
            order.setOrderDate(LocalDate.parse(record.get("Order Date"), df));
            order.setShipDate(LocalDate.parse(record.get("Ship Date"), df));
            order.setTotalProfit(new BigDecimal(record.get("Total Profit")));

            //validate
            Set violations = validator.validate(order);
            if (!violations.isEmpty()) throw new Exception("Failed validation:" + violations.toString());

        } catch (Exception e) {
            System.out.println("Error with row: " + record.toString() + e.getMessage());
            return null;
        }
        return order;
    }
}
