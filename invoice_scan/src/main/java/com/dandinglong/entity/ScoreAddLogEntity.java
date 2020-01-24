package com.dandinglong.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "score_add_log")
public class ScoreAddLogEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "orginal_user_id")
    private Integer orginalUserId;
    @Column(name = "add_score")
    private Integer addScore;
    @Column(name = "insert_time")
    private Date insertTime;

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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

    public Integer getOrginalUserId() {
        return orginalUserId;
    }

    public void setOrginalUserId(Integer orginalUserId) {
        this.orginalUserId = orginalUserId;
    }

    public Integer getAddScore() {
        return addScore;
    }

    public void setAddScore(Integer addScore) {
        this.addScore = addScore;
    }
}
