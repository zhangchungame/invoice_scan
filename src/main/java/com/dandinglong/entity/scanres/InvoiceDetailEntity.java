package com.dandinglong.entity.scanres;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "invoice_detail")
public class InvoiceDetailEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "invoice_id")
    private Integer invoiceId;
    @Column(name = "CommodityAmount")
    private String CommodityAmount;
    @Column(name = "CommodityName")
    private String CommodityName;
    @Column(name = "CommodityNum")
    private String CommodityNum;
    @Column(name = "CommodityTax")
    private String CommodityTax;
    @Column(name = "CommodityType")
    private String CommodityType;
    @Column(name = "CommodityTaxRate")
    private String CommodityTaxRate;
    @Column(name = "CommodityUnit")
    private String CommodityUnit;
    @Column(name = "insert_time")
    private Date insertTime;

    public String getCommodityUnit() {
        return CommodityUnit;
    }

    public void setCommodityUnit(String commodityUnit) {
        CommodityUnit = commodityUnit;
    }

    public String getCommodityType() {
        return CommodityType;
    }

    public void setCommodityType(String commodityType) {
        CommodityType = commodityType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCommodityAmount() {
        return CommodityAmount;
    }

    public void setCommodityAmount(String commodityAmount) {
        CommodityAmount = commodityAmount;
    }

    public String getCommodityName() {
        return CommodityName;
    }

    public void setCommodityName(String commodityName) {
        CommodityName = commodityName;
    }

    public String getCommodityNum() {
        return CommodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        CommodityNum = commodityNum;
    }

    public String getCommodityTax() {
        return CommodityTax;
    }

    public void setCommodityTax(String commodityTax) {
        CommodityTax = commodityTax;
    }

    public String getCommodityTaxRate() {
        return CommodityTaxRate;
    }

    public void setCommodityTaxRate(String commodityTaxRate) {
        CommodityTaxRate = commodityTaxRate;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
