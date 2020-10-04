package com.example.shashi.playcsv.validator;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class Rule {

    public static CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // ID (must be unique)
                new NotNull(), // currencyCode
                new Optional(), // BIC
                new Optional(), // bundleId
                new NotNull(), // customerId
                new ParseDate("mm/dd/yyyy") };
        return processors;
    }
}
