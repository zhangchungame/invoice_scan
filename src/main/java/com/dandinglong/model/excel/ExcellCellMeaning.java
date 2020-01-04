package com.dandinglong.model.excel;

/**
 * 每一列的属性
 */
public class ExcellCellMeaning {
    private int cellNum;
    private String fieldName;
    private String description;

    public ExcellCellMeaning(int cellNum, String fieldName, String description) {
        this.cellNum = cellNum;
        this.fieldName = fieldName;
        this.description = description;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
