package com.dandinglong.entity.scanres;

import com.dandinglong.entity.baidu.*;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Table(name = "invoice_data")
public class InvoiceDataEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "AmountInWords")
    private String AmountInWords;
    @Column(name = "NoteDrawer")
    private String NoteDrawer;
    @Column(name = "SellerAddress")
    private String SellerAddress;
    @Column(name = "SellerRegisterNum")
    private String SellerRegisterNum;
    @Column(name = "SellerBank")
    private String SellerBank;
    @Column(name = "Remarks")
    private String Remarks;
    @Column(name = "TotalTax")
    private String TotalTax;
    @Column(name = "CheckCode")
    private String CheckCode;
    @Column(name = "InvoiceCode")
    private String InvoiceCode;
    @Column(name = "InvoiceDate")
    private String InvoiceDate;
    @Column(name = "PurchaserRegisterNum")
    private String PurchaserRegisterNum;
    @Column(name = "InvoiceTypeOrg")
    private String InvoiceTypeOrg;
    @Column(name = "Password")
    private String Password;
    @Column(name = "PurchaserBank")
    private String PurchaserBank;
    @Column(name = "AmountInFiguers")
    private String AmountInFiguers;
    @Column(name = "Checker")
    private String Checker;
    @Column(name = "TotalAmount")
    private String TotalAmount;
    @Column(name = "PurchaserName")
    private String PurchaserName;
    @Column(name = "InvoiceType")
    private String InvoiceType;
    @Column(name = "PurchaserAddress")
    private String PurchaserAddress;
    @Column(name = "Payee")
    private String Payee;
    @Column(name = "SellerName")
    private String SellerName;
    @Column(name = "InvoiceNum")
    private String InvoiceNum;
    @Column(name = "insert_time")
    private Date insertTime;

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

    public String getAmountInWords() {
        return AmountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        AmountInWords = amountInWords;
    }

    public String getNoteDrawer() {
        return NoteDrawer;
    }

    public void setNoteDrawer(String noteDrawer) {
        NoteDrawer = noteDrawer;
    }

    public String getSellerAddress() {
        return SellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        SellerAddress = sellerAddress;
    }

    public String getSellerRegisterNum() {
        return SellerRegisterNum;
    }

    public void setSellerRegisterNum(String sellerRegisterNum) {
        SellerRegisterNum = sellerRegisterNum;
    }

    public String getSellerBank() {
        return SellerBank;
    }

    public void setSellerBank(String sellerBank) {
        SellerBank = sellerBank;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTotalTax() {
        return TotalTax;
    }

    public void setTotalTax(String totalTax) {
        TotalTax = totalTax;
    }

    public String getCheckCode() {
        return CheckCode;
    }

    public void setCheckCode(String checkCode) {
        CheckCode = checkCode;
    }

    public String getInvoiceCode() {
        return InvoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        InvoiceCode = invoiceCode;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public String getPurchaserRegisterNum() {
        return PurchaserRegisterNum;
    }

    public void setPurchaserRegisterNum(String purchaserRegisterNum) {
        PurchaserRegisterNum = purchaserRegisterNum;
    }

    public String getInvoiceTypeOrg() {
        return InvoiceTypeOrg;
    }

    public void setInvoiceTypeOrg(String invoiceTypeOrg) {
        InvoiceTypeOrg = invoiceTypeOrg;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPurchaserBank() {
        return PurchaserBank;
    }

    public void setPurchaserBank(String purchaserBank) {
        PurchaserBank = purchaserBank;
    }

    public String getAmountInFiguers() {
        return AmountInFiguers;
    }

    public void setAmountInFiguers(String amountInFiguers) {
        AmountInFiguers = amountInFiguers;
    }

    public String getChecker() {
        return Checker;
    }

    public void setChecker(String checker) {
        Checker = checker;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getPurchaserName() {
        return PurchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        PurchaserName = purchaserName;
    }

    public String getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        InvoiceType = invoiceType;
    }

    public String getPurchaserAddress() {
        return PurchaserAddress;
    }

    public void setPurchaserAddress(String purchaserAddress) {
        PurchaserAddress = purchaserAddress;
    }

    public String getPayee() {
        return Payee;
    }

    public void setPayee(String payee) {
        Payee = payee;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getInvoiceNum() {
        return InvoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        InvoiceNum = invoiceNum;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
