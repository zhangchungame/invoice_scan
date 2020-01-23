package com.dandinglong.config;

import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.model.MessageBrokerComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class UploadFileQueueConfig  implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof MessageBrokerComponent){
            ((MessageBrokerComponent) bean).init();
        }
        return bean;
    }
}
