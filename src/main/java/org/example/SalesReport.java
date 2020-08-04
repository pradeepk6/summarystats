package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.stream.StreamSupport;

public class SalesReport {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Reader in = new FileReader("src/test/resources/SalesRecords.csv");
        Iterable<CSVRecord> iterable =
                CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreEmptyLines(true)
                        .withDelimiter(',')
                        .withTrim()
                        .parse(in);
        CustomSummaryStatistics stats = StreamSupport
                .stream(iterable.spliterator(), true)
                .map(CSVRecord::toMap)
                .filter(map -> validate(map))
                .collect(CustomSummaryStatistics.newCollector());
        System.out.println(stats.toString());
        System.out.println("Time(milli-seconds) taken to generate Sales Report : " + (System.currentTimeMillis() - start));
    }

    public static boolean validate(Map<String,String> map) {
        try {
            //todo validate each row
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
