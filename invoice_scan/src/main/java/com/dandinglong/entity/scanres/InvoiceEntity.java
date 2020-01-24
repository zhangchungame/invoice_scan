package com.dandinglong.entity.scanres;

import java.util.List;

public class InvoiceEntity {
    private InvoiceDataEntity invoiceDataEntity;
    private List<InvoiceDetailEntity> invoiceDetailEntityList;

    public InvoiceDataEntity getInvoiceDataEntity() {
        return invoiceDataEntity;
    }

    public void setInvoiceDataEntity(InvoiceDataEntity invoiceDataEntity) {
        this.invoiceDataEntity = invoiceDataEntity;
    }

    public List<InvoiceDetailEntity> getInvoiceDetailEntityList() {
        return invoiceDetailEntityList;
    }

    public void setInvoiceDetailEntityList(List<InvoiceDetailEntity> invoiceDetailEntityList) {
        this.invoiceDetailEntityList = invoiceDetailEntityList;
    }
}
