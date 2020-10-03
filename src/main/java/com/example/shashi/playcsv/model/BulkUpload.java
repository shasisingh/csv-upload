package com.example.shashi.playcsv.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@SuppressWarnings("pmd:ImplementEqualsHashCodeOnValueObjects")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkUpload {

    private String iban;
    private String currencyCode;
    private String bicCode;
    private String bundleCode;
    private String customerId;
    private Date processingDate;
    private ValidationError validationError;

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBicCode() {
        return bicCode;
    }

    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
    }

    public String getBundleCode() {
        return bundleCode;
    }

    public void setBundleCode(String bundleCode) {
        this.bundleCode = bundleCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ValidationError getValidationError() {
        return validationError;
    }

    public void setValidationError(ValidationError validationError) {
        this.validationError = validationError;
    }

    @Override
    public String toString() {
        return "BulkUpload{" + "iban='" + iban + '\'' + ", currencyCode='" + currencyCode + '\'' + ", bicCode='" + bicCode + '\'' + ", bundleCode='"
                + bundleCode + '\'' + ", customerId='" + customerId + '\'' + ", processingDate=" + processingDate + ", validationError="
                + validationError + '}';
    }
}
