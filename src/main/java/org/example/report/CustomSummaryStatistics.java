package org.example.report;

import java.math.BigDecimal;
import java.time.Period;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class CustomSummaryStatistics implements Consumer<Order> {

    private Map<String, BigDecimal> itemWiseProfits = new HashMap<>();
    private Optional<Map.Entry<Integer, Integer>> yearWithMaxOrders;
    private int avgDaysBetweenOrderDateAndShipDate;

    private Map<Integer, Integer> yearWiseOrdersMap = new HashMap<>();
    private long totalDaysBetweenOrderDateAndShipDate;
    private long numRecords;

    public static Collector<Order, ?, CustomSummaryStatistics> newCollector() {
        return Collector.of(CustomSummaryStatistics::new, CustomSummaryStatistics::accept,
                CustomSummaryStatistics::combine, CustomSummaryStatistics::finisher);
    }

    @Override
    public void accept(Order order) {
        updateItemWiseProfits(order);
        updateYearWithHighestOrders(order);
        updateAvgDaysBetweenOrderDateAndShipDate(order);
    }

    private void updateItemWiseProfits(Order order) {
        itemWiseProfits.merge(order.getItemType(), order.getTotalProfit(), BigDecimal::add);
    }

    private void updateYearWithHighestOrders(Order order) {
        yearWiseOrdersMap.merge(order.getOrderDate().getYear(), 1, Integer::sum);
    }

    private void updateAvgDaysBetweenOrderDateAndShipDate(Order order) {
        numRecords++;
        totalDaysBetweenOrderDateAndShipDate += Period.between(order.getOrderDate(), order.getShipDate()).getDays();
    }

    public CustomSummaryStatistics combine(CustomSummaryStatistics other) {
        other.itemWiseProfits.forEach((k, v) -> itemWiseProfits.merge(k, v, BigDecimal::add));
        other.yearWiseOrdersMap.forEach((k, v) -> yearWiseOrdersMap.merge(k, v, Integer::sum));
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

    public long getNumRecords() {
        return numRecords;
    }

    @Override
    public String toString() {
        return "CustomSummaryStatistics{" +
                "itemWiseProfits=" + itemWiseProfits +
                ", yearWithMaxOrders=" + yearWithMaxOrders +
                ", avgDaysBetweenOrderDateAndShipDate=" + avgDaysBetweenOrderDateAndShipDate +
                ", numRecords=" + numRecords +
                '}';
    }
}
