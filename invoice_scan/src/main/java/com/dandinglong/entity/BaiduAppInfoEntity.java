package com.dandinglong.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "baidu_app_info")
public class BaiduAppInfoEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_key")
    private String appKey;
    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "invoice_used_num")
    private Integer invoiceUsedNum;

    public Integer getInvoiceUsedNum() {
        return invoiceUsedNum;
    }

    public void setInvoiceUsedNum(Integer invoiceUsedNum) {
        this.invoiceUsedNum = invoiceUsedNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
