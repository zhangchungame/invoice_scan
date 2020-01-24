package com.danidnglong.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dandinglong.dto.UploadFileConsumerMessageDto;
import com.dandinglong.dto.UploadFileProducerMessageDto;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.WxException;
import com.dandinglong.model.ocrmodel.OcrModel;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OcrProcessService {
    private Logger logger = LoggerFactory.getLogger(OcrProcessService.class);

    @Value("${tmpFilePath}")
    private String tmpFilePath;
    @Value("${scan.host}")
    private String host;


    /**
     * 处理定时识别任务
     */
    public void ocrProcess() {
        try {
            UploadFileProducerMessageDto uploadFileProducerMessageDto = requestForDto();
            if(uploadFileProducerMessageDto==null){
                return;
            }
            Class ocrModelClass = ImgTypeEnum.getEnumByType(uploadFileProducerMessageDto.getUploadFileEntity().getType()).getOcrModelClass();
            OcrModel ocrModel = (OcrModel) ocrModelClass.newInstance();
            String recognition = ocrModel.recognition(uploadFileProducerMessageDto,tmpFilePath);
            logger.info("识别结果：{}",recognition);
            UploadFileConsumerMessageDto uploadFileConsumerMessageDto=new UploadFileConsumerMessageDto(uploadFileProducerMessageDto.getUploadFileEntity(),recognition);
            feedBackForDto(uploadFileConsumerMessageDto);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        } catch (InstantiationException e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
     * 请求获取任务
     *
     * @return
     * @throws IOException
     */
    public UploadFileProducerMessageDto requestForDto() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(host + "/ocr/getOcrTask").build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String resp = response.body().string();
        response.close();
        JSONObject jsonObject = JSONObject.parseObject(resp);
        if(jsonObject.getInteger("code")==0){
            logger.info("请求到数据 {}", resp);
            JSONObject data = jsonObject.getJSONObject("data");
            UploadFileProducerMessageDto uploadFileProducerMessageDto = JSON.parseObject(data.toJSONString(), UploadFileProducerMessageDto.class);
            return uploadFileProducerMessageDto;
        }else {
            return null;
        }
    }

    public void feedBackForDto(UploadFileConsumerMessageDto uploadFileConsumerMessageDto) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON.toJSONString(uploadFileConsumerMessageDto), MediaType.parse("application/json"));
        Request request = new Request.Builder().url(host + "/ocr/feedBackOcrTask").post(requestBody).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();

    }

}
