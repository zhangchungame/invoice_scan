package com.dandinglong.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "baidu_app_process_times")
public class BaiduAppProcessTimesEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "scan_type")
    private String scanType;
    @Column(name = "process_times")
    private Integer processTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public Integer getProcessTimes() {
        return processTimes;
    }

    public void setProcessTimes(Integer processTimes) {
        this.processTimes = processTimes;
    }
}
