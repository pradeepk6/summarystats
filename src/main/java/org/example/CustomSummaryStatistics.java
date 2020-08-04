package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class CustomSummaryStatistics implements Consumer<Map<String, String>> {

    private Map<String, BigDecimal> itemWiseProfits = new ConcurrentHashMap<>();
    private Optional<Map.Entry<Integer, Integer>> yearWithMaxOrders;
    private int avgDaysBetweenOrderDateAndShipDate;

    private Map<Integer, Integer> yearWiseOrdersMap = new ConcurrentHashMap<>();
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("M/d/y");
    private long totalDaysBetweenOrderDateAndShipDate;
    private long numRecords;

    public static Collector<Map<String, String>, ?, CustomSummaryStatistics> newCollector() {
        return Collector.of(CustomSummaryStatistics::new, CustomSummaryStatistics::accept,
                CustomSummaryStatistics::combine, CustomSummaryStatistics::finisher);
    }

    @Override
    public void accept(Map<String, String> map) {
        updateItemWiseProfits(map);
        updateYearWithHighestOrders(map);
        updateAvgDaysBetweenOrderDateAndShipDate(map);
    }

    private void updateItemWiseProfits(Map<String, String> map) {
        itemWiseProfits.merge(map.get("Item Type"), new BigDecimal(map.get("Total Profit")), BigDecimal::add);
    }

    private void updateYearWithHighestOrders(Map<String, String> map) {
        yearWiseOrdersMap.merge(getYear(map.get("Order Date")), 1, Integer::sum);
    }

    private Integer getYear(String date) {
        return Integer.valueOf(LocalDate.parse(date, df).getYear());
    }

    private void updateAvgDaysBetweenOrderDateAndShipDate(Map<String, String> map) {
        numRecords++;
        totalDaysBetweenOrderDateAndShipDate += getDateDiff(map.get("Order Date"), map.get("Ship Date"));
    }

    private int getDateDiff(String orderDate, String shipDate) {
        return Period.between(LocalDate.parse(orderDate, df), LocalDate.parse(shipDate, df)).getDays();
    }

    public CustomSummaryStatistics combine(CustomSummaryStatistics other) {
        other.itemWiseProfits.entrySet().forEach(
                entry -> itemWiseProfits.merge(entry.getKey(), entry.getValue(), BigDecimal::add)
        );
        other.yearWiseOrdersMap.entrySet().forEach(
                entry -> yearWiseOrdersMap.merge(entry.getKey(), entry.getValue(), Integer::sum)
        );
        numRecords += other.numRecords;
        totalDaysBetweenOrderDateAndShipDate += other.totalDaysBetweenOrderDateAndShipDate;
        return this;
    }

    public CustomSummaryStatistics finisher() {
        yearWithMaxOrders = yearWiseOrdersMap.entrySet().stream()
                .max(Map.Entry.comparingByValue(Comparator.comparing(entry -> entry.longValue())));
        avgDaysBetweenOrderDateAndShipDate = (int) (totalDaysBetweenOrderDateAndShipDate / numRecords);
        return this;
    }

    public Map<String, BigDecimal> getItemWiseProfits() {
        return itemWiseProfits;
    }

    public int getAvgDaysBetweenOrderDateAndShipDate() {
        return avgDaysBetweenOrderDateAndShipDate;
    }

    public Optional<Map.Entry<Integer, Integer>> getYearWithMaxOrders() {
        return yearWithMaxOrders;
    }

    @Override
    public String toString() {
        return "CustomSummaryStatistics{" +
                "itemWiseProfits=" + itemWiseProfits +
                ", yearWithMaxOrders=" + yearWithMaxOrders +
                ", avgDaysBetweenOrderDateAndShipDate=" + avgDaysBetweenOrderDateAndShipDate +
                '}';
    }
}
