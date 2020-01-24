package com.dandinglong.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "scan_image_by_day_total")
public class ScanImageByDayTotalEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
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
    @Column(name = "insert_time")
    private Date insertTime;
    @Column(name = "update_time")
    private Date updateTime;

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
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
