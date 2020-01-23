package com.dandinglong.model;

import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.mapper.UploadFileMapper;
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
    private ConcurrentLinkedQueue<UploadFileEntity> queue = new ConcurrentLinkedQueue<>();

    public void init(){
        Example example=new Example(UploadFileEntity.class);
        example.createCriteria().andEqualTo("step","0");
        example.orderBy("id").asc();
        List<UploadFileEntity> uploadFileEntities = uploadFileMapper.selectByExample(example);
        Iterator<UploadFileEntity> iterator = uploadFileEntities.iterator();
        while (iterator.hasNext()){
            UploadFileEntity next = iterator.next();
            queue.offer(next);
        }
    }

    public boolean queueOffer(UploadFileEntity uploadFileEntity){
        return queue.offer(uploadFileEntity);
    }
    public UploadFileEntity queuePoll(){
        UploadFileEntity poll = queue.poll();
        poll.setStep(1);
        uploadFileMapper.updateByPrimaryKey(poll);
        return poll;
    }
}
