package com.example.shashi.playcsv.service;

import com.example.shashi.playcsv.model.BulkUpload;
import com.example.shashi.playcsv.model.CsvFile;
import com.example.shashi.playcsv.model.ValidationError;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvConstraintViolationException;
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
            while ((bulkUpload = getRead(beanReader, nameMapping, processors)) != null) {
                bulkUploads.add(bulkUpload);
            }
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
        return bulkUploads;
    }

    private BulkUpload getRead(CsvBeanReader beanReader, String[] nameMapping, CellProcessor[] processors) throws IOException {
        BulkUpload res=new BulkUpload();
        try {
            res = beanReader.read(BulkUpload.class, nameMapping, processors);
            System.out.printf("lineNo=%s, rowNo=%s, response=%s%n", beanReader.getLineNumber(), beanReader.getRowNumber(), res);
        }catch (SuperCsvConstraintViolationException e){
            res.setValidationError(validationError(e.getMessage(),beanReader.getLineNumber(), beanReader.getRowNumber()));
            System.out.printf("lineNo=%s, rowNo=%s, error=%s", beanReader.getLineNumber(), beanReader.getRowNumber(), e.getMessage());
        }catch (RuntimeException e){
            res.setValidationError(validationError(e.getMessage(),beanReader.getLineNumber(), beanReader.getRowNumber()));
            System.out.printf("lineNo=%s, rowNo=%s,error=%s", beanReader.getLineNumber(), beanReader.getRowNumber(), e.getMessage());
        }
        return res;
    }

    private ValidationError validationError(String errorDesc,int lineNumber,int rowNumber){
        ValidationError validationError= new ValidationError();
        CsvFile csvFile= new CsvFile();
        csvFile.setRowNo(rowNumber);
        csvFile.setLineNo(lineNumber);
        validationError.setKey(csvFile);
        validationError.setErrorDesc(errorDesc);
        return validationError;
    }

}
