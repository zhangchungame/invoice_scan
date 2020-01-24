package com.dandinglong.model.ocrmodel;

import com.dandinglong.dto.UploadFileProducerMessageDto;

public interface OcrModel {
    public String recognition(UploadFileProducerMessageDto uploadFileProducerMessageDto, String tmpFilePath);
}
