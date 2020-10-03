package com.example.shashi.playcsv.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("pmd:ImplementEqualsHashCodeOnValueObjects")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CsvFile {
    private int lineNo;
    private int rowNo;

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    @Override
    public String toString() {
        return "CsvFile{" + "lineNo=" + lineNo + ", rowNo=" + rowNo + '}';
    }
}
