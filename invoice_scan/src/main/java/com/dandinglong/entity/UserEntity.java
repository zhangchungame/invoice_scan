package com.dandinglong.entity;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user")
public class UserEntity {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    @Column(name = "open_id")
    private String openId;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "country")
    private String country;
    @Column(name = "gender")
    private String gender;
    @Column(name = "today_used_score")
    private Integer todayUsedScore;
    @Column(name = "free_score_for_day")
    private Integer freeScoreForDay;
    @Column(name = "show_welcome")
    private Integer showWelcome;
    @Column(name = "share_img_url")
    private String shareImgUrl;
    @Column(name = "share_img_update_time")
    private Date shareImgUpdateTime;

    @Column(name = "orginal_user_id")
    private Integer orginalUserId;

    @Column(name = "register_time")
    private Date registerTime;
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    public Integer getShowWelcome() {
        return showWelcome;
    }

    public String getShareImgUrl() {
        return shareImgUrl;
    }

    public Integer getOrginalUserId() {
        return orginalUserId;
    }

    public void setOrginalUserId(Integer orginalUserId) {
        this.orginalUserId = orginalUserId;
    }

    public void setShareImgUrl(String shareImgUrl) {
        this.shareImgUrl = shareImgUrl;
    }

    public Date getShareImgUpdateTime() {
        return shareImgUpdateTime;
    }

    public void setShareImgUpdateTime(Date shareImgUpdateTime) {
        this.shareImgUpdateTime = shareImgUpdateTime;
    }

    public void setShowWelcome(Integer showWelcome) {
        this.showWelcome = showWelcome;
    }

    public Integer getTodayUsedScore() {
        return todayUsedScore;
    }

    public void setTodayUsedScore(Integer todayUsedScore) {
        this.todayUsedScore = todayUsedScore;
    }

    public Integer getFreeScoreForDay() {
        return freeScoreForDay;
    }

    public void setFreeScoreForDay(Integer freeScoreForDay) {
        this.freeScoreForDay = freeScoreForDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
