package com.example.shashi.playcsv.service;

import com.example.shashi.playcsv.model.BulkUpload;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static com.example.shashi.playcsv.validator.Rule.getProcessors;

@Service
public class CSVParser {

    public  List<BulkUpload> readCSVToBean(Reader reader, CsvPreference csvPreference) {
        List<BulkUpload> bulkUploads = new ArrayList<>();
        try (CsvBeanReader beanReader = new CsvBeanReader(reader, csvPreference)) {
            final String[] nameMapping = new String[] { "iban", "currencyCode", "bicCode", "bundleCode", "customerId", "processingDate" };
            beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();
            BulkUpload bulkUpload;
            while ((bulkUpload = beanReader.read(BulkUpload.class, nameMapping, processors)) != null) {
                System.out.printf("lineNo=%s, rowNo=%s, response=%s%n", beanReader.getLineNumber(), beanReader.getRowNumber(), bulkUpload);
                bulkUploads.add(bulkUpload);
            }

        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
        return bulkUploads;
    }

}
