package com.dandinglong.model.ocrmodel;

import com.daidu.aip.ocr.MyAipOcr;
import com.dandinglong.dto.UploadFileProducerMessageDto;
import com.dandinglong.model.ImageCompressThumb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

public class InvoiceOcrModel extends BaseOcrModel implements OcrModel {
    private Logger logger = LoggerFactory.getLogger(InvoiceOcrModel.class);


    @Override
    public String recognition(UploadFileProducerMessageDto uploadFileProducerMessageDto, String tmpFilePath) {
        try {
            String downLoadFile = downLoadFile(uploadFileProducerMessageDto.getUploadFileEntity().getFileName(),tmpFilePath);
            ImageCompressThumb imageCompressThumb = new ImageCompressThumb();
            imageCompressThumb.compress(tmpFilePath+downLoadFile);

            String ocr = ocr(uploadFileProducerMessageDto.getToken(), tmpFilePath + downLoadFile);
            return ocr;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }
    public String ocr(String token, String filePathName){

        MyAipOcr client=new MyAipOcr("","","");
        client.setToken(token);
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("accuracy", "high");
        org.json.JSONObject res = client.vatInvoice(filePathName, options);
        String result = res.toString(2);
        return result;
    }
}
