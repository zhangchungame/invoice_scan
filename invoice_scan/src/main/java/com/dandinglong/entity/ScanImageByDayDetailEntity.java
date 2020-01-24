package com.dandinglong.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "scan_image_by_day_detail")
public class ScanImageByDayDetailEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "total_id")
    private Integer totalId;
    @Column(name = "type")
    private String type;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "deal_date")
    private String dealDate;
    @Column(name = "upload_num")
    private Integer uploadNum;
    @Column(name = "scan_num")
    private Integer scanNum;
    @Column(name = "fail_num")
    private Integer failNum;
    @Column(name = "excel_step")
    private Integer excelStep;
    @Column(name = "excel_file")
    private String excelFile;
    @Column(name = "insert_time")
    private Date insertTime;
    @Column(name = "update_time")
    private Date updateTime;

    public Integer getTotalId() {
        return totalId;
    }

    public void setTotalId(Integer totalId) {
        this.totalId = totalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public String getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }

    public Integer getExcelStep() {
        return excelStep;
    }

    public void setExcelStep(Integer excelStep) {
        this.excelStep = excelStep;
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

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public Integer getUploadNum() {
        return uploadNum;
    }

    public void setUploadNum(Integer uploadNum) {
        this.uploadNum = uploadNum;
    }

    public Integer getScanNum() {
        return scanNum;
    }

    public void setScanNum(Integer scanNum) {
        this.scanNum = scanNum;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
