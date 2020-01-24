package com.dandinglong.model;

import com.dandinglong.dto.UploadFileProducerMessageDto;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.service.AipOcrClientSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MessageBrokerComponent {
    @Autowired
    private UploadFileMapper uploadFileMapper;
    private ConcurrentLinkedQueue<UploadFileProducerMessageDto> queue = new ConcurrentLinkedQueue<>();
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;

    public void init(){
        Example example=new Example(UploadFileEntity.class);
        example.createCriteria().andEqualTo("step","0");
        example.orderBy("id").asc();
        List<UploadFileEntity> uploadFileEntities = uploadFileMapper.selectByExample(example);
        Iterator<UploadFileEntity> iterator = uploadFileEntities.iterator();
        while (iterator.hasNext()){
            UploadFileEntity next = iterator.next();
            String token=aipOcrClientSelector.getAvailableInvoiceClient(aipOcrClientSelector.getProcessTimes(1)).getToken();
            UploadFileProducerMessageDto uploadFileProducerMessageDto =new UploadFileProducerMessageDto(next,token);
            queue.offer(uploadFileProducerMessageDto);
        }
    }

    public boolean queueOffer(UploadFileProducerMessageDto uploadFileProducerMessageDto){
        return queue.offer(uploadFileProducerMessageDto);
    }
    public UploadFileProducerMessageDto queuePoll(){
        UploadFileProducerMessageDto poll = queue.poll();
        if(poll==null){
            return null;
        }
        poll.getUploadFileEntity().setStep(1);
        uploadFileMapper.updateByPrimaryKey(poll.getUploadFileEntity());
        return poll;
    }
}
