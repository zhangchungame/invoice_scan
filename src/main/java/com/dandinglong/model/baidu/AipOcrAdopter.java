package com.dandinglong.model.baidu;

import com.baidu.aip.ocr.AipOcr;
import com.dandinglong.entity.BaiduAppInfoEntity;

public class AipOcrAdopter {
    private BaiduAppInfoEntity baiduAppInfoEntity;
    private AipOcr aipOcr;

    public AipOcrAdopter(BaiduAppInfoEntity baiduAppInfoEntity, AipOcr aipOcr) {
        this.baiduAppInfoEntity = baiduAppInfoEntity;
        this.aipOcr = aipOcr;
    }

    public BaiduAppInfoEntity getBaiduAppInfoEntity() {
        return baiduAppInfoEntity;
    }

    public AipOcr getAipOcr() {
        return aipOcr;
    }

    public void setBaiduAppInfoEntity(BaiduAppInfoEntity baiduAppInfoEntity) {
        this.baiduAppInfoEntity = baiduAppInfoEntity;
    }

    public void setAipOcr(AipOcr aipOcr) {
        this.aipOcr = aipOcr;
    }
}
