package com.example.shashi.playcsv.service;

import com.example.shashi.playcsv.model.BulkUpload;
import com.example.shashi.playcsv.model.CsvFile;
import com.example.shashi.playcsv.model.ValidationError;
import com.example.shashi.playcsv.model.ValidationStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.shashi.playcsv.validator.Rule.getProcessors;

@Service
public class CSVParser {

    @Value("${csv.bulk.upload.column.name}")
    private String[] nameMapping;

    public List<BulkUpload> readCSVToBean(Reader reader, CsvPreference csvPreference) {
        List<BulkUpload> bulkUploads = new ArrayList<>();
        try (CsvBeanReader beanReader = new CsvBeanReader(reader, csvPreference)) {
            beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();
            new BulkUpload();
            BulkUpload bulkUpload;
            while ((bulkUpload = getRead(beanReader, nameMapping, processors)) != null) {
                bulkUploads.add(bulkUpload);
            }
        } catch (IOException e) {
            System.err.println("Error" + e.getMessage());
        }
        return bulkUploads;
    }

    private BulkUpload getRead(CsvBeanReader beanReader, String[] nameMapping, CellProcessor[] processors) throws IOException {
        BulkUpload res = new BulkUpload();
        try {
            res = beanReader.read(BulkUpload.class, nameMapping, processors);
            if (res != null) {
                ValidationError validationError = validateBean(res, beanReader.getLineNumber(), beanReader.getRowNumber());
                if (validationError == null) {
                    res.setValidationStatus(ValidationStatus.SUCCESS);
                    res.setValidationError(null);
                } else {
                    res.setValidationError(validationError);
                    res.setValidationStatus(ValidationStatus.FAILED);
                }
                System.out.print(String.format("lineNo=%s, rowNo=%s, response=%s%n", beanReader.getLineNumber(), beanReader.getRowNumber(), res));
            }
        } catch (SuperCsvConstraintViolationException e) {
            res.setValidationError(validationError(e.getMessage(), beanReader.getLineNumber(), beanReader.getRowNumber()));
            res.setValidationStatus(ValidationStatus.FAILED);
            System.out.println(String.format("lineNo=%s, rowNo=%s, error=%s", beanReader.getLineNumber(), beanReader.getRowNumber(), e.getMessage()));
        } catch (RuntimeException e) {
            res.setValidationStatus(ValidationStatus.FAILED);
            res.setValidationError(validationError(e.getMessage(), beanReader.getLineNumber(), beanReader.getRowNumber()));
            System.out.println(String.format("lineNo=%s, rowNo=%s,error=%s", beanReader.getLineNumber(), beanReader.getRowNumber(), e.getMessage()));
        }
        return res;
    }

    private ValidationError validationError(String errorDesc, int lineNumber, int rowNumber) {
        ValidationError validationError = new ValidationError();
        CsvFile csvFile = new CsvFile();
        csvFile.setRowNo(rowNumber);
        csvFile.setLineNo(lineNumber);
        validationError.setKey(csvFile);
        validationError.setErrorDesc(Collections.singletonList(errorDesc));
        return validationError;
    }

    private ValidationError validateBean(BulkUpload bulkUpload, int lineNumber, int rowNumber) {

        ValidationError validationError = new ValidationError();
        List<String> listOfError = new ArrayList<>();
        if (!validateIBAN(bulkUpload.getIban())) {
            listOfError.add("invalid Iban format.");
        }
        if (!validateBic(bulkUpload.getBicCode())) {
            listOfError.add("invalid BIC code ( min=8,max=11).");
        }
        if (!validateCurrencyCode(bulkUpload.getCurrencyCode())) {
            listOfError.add("invalid Currency code ( min=3,max=3).");
        }
        if (!validateCustomerId(bulkUpload.getCustomerId())) {
            listOfError.add("invalid customerId code.");
        }
        if (listOfError.isEmpty()) {
            return null;
        } else {
            validationError.setErrorDesc(listOfError);
            CsvFile csvFile = new CsvFile();
            csvFile.setRowNo(rowNumber);
            csvFile.setLineNo(lineNumber);
            validationError.setKey(csvFile);
            return validationError;
        }
    }

    private boolean validateIBAN(final String iban) {
        Pattern pattern = Pattern.compile("[A-Z0-9]{18}");
        Matcher matcher = pattern.matcher(iban);
        return matcher.matches();
    }

    private boolean validateBic(final String bic) {
        Pattern pattern = Pattern.compile("[A-Z0-9]{8,11}");
        Matcher matcher = pattern.matcher(bic);
        return matcher.matches();
    }

    private boolean validateCurrencyCode(final String code) {
        Pattern pattern = Pattern.compile("[A-Z]{3}");
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    private boolean validateCustomerId(final String customerId) {
        Pattern pattern = Pattern.compile("[0-9]{1,15}");
        Matcher matcher = pattern.matcher(customerId);
        return matcher.matches();
    }

    private boolean validateData(final String var1) {
        Pattern pattern = Pattern.compile("[A-Za-z]");
        Matcher matcher = pattern.matcher(var1);
        return matcher.matches();
    }

}
