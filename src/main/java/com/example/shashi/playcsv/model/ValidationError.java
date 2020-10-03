package com.example.shashi.playcsv.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("pmd:ImplementEqualsHashCodeOnValueObjects")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {
    private CsvFile key;
    private String errorDesc;

    public CsvFile getKey() {
        return key;
    }

    public void setKey(CsvFile key) {
        this.key = key;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Override
    public String toString() {
        return "ValidationError{" + "key=" + key + ", errorDesc='" + errorDesc + '\'' + '}';
    }
}
