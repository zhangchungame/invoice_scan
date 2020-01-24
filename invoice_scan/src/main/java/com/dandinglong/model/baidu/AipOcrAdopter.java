package com.dandinglong.model.baidu;

import com.baidu.aip.ocr.AipOcr;
import com.daidu.aip.ocr.MyAipOcr;
import com.dandinglong.entity.BaiduAppInfoEntity;

public class AipOcrAdopter {
    private BaiduAppInfoEntity baiduAppInfoEntity;
    private MyAipOcr aipOcr;

    public AipOcrAdopter(BaiduAppInfoEntity baiduAppInfoEntity, MyAipOcr aipOcr) {
        this.baiduAppInfoEntity = baiduAppInfoEntity;
        this.aipOcr = aipOcr;
    }

    public BaiduAppInfoEntity getBaiduAppInfoEntity() {
        return baiduAppInfoEntity;
    }

    public void setBaiduAppInfoEntity(BaiduAppInfoEntity baiduAppInfoEntity) {
        this.baiduAppInfoEntity = baiduAppInfoEntity;
    }

    public MyAipOcr getAipOcr() {
        return aipOcr;
    }

    public void setAipOcr(MyAipOcr aipOcr) {
        this.aipOcr = aipOcr;
    }
}
