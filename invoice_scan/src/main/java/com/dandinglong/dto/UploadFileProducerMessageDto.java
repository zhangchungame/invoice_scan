package com.dandinglong.dto;

import com.dandinglong.entity.UploadFileEntity;

public class UploadFileProducerMessageDto {
    private UploadFileEntity uploadFileEntity;
    private String token;

    public UploadFileProducerMessageDto(UploadFileEntity uploadFileEntity, String token) {
        this.uploadFileEntity = uploadFileEntity;
        this.token = token;
    }

    public UploadFileEntity getUploadFileEntity() {
        return uploadFileEntity;
    }

    public void setUploadFileEntity(UploadFileEntity uploadFileEntity) {
        this.uploadFileEntity = uploadFileEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
