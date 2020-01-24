package com.dandinglong.dto;

import com.dandinglong.entity.UploadFileEntity;

public class UploadFileConsumerMessageDto {
    private UploadFileEntity uploadFileEntity;
    private String ocrResult;

    public UploadFileConsumerMessageDto(UploadFileEntity uploadFileEntity, String ocrResult) {
        this.uploadFileEntity = uploadFileEntity;
        this.ocrResult = ocrResult;
    }

    public UploadFileEntity getUploadFileEntity() {
        return uploadFileEntity;
    }

    public void setUploadFileEntity(UploadFileEntity uploadFileEntity) {
        this.uploadFileEntity = uploadFileEntity;
    }

    public String getOcrResult() {
        return ocrResult;
    }

    public void setOcrResult(String ocrResult) {
        this.ocrResult = ocrResult;
    }
}
