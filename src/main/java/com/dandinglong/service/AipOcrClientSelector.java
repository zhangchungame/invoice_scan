package com.dandinglong.service;

import com.baidu.aip.ocr.AipOcr;
import com.dandinglong.entity.BaiduAppInfoEntity;
import com.dandinglong.entity.BaiduAppProcessTimesEntity;
import com.dandinglong.exception.NoBaiduAppCanUseException;
import com.dandinglong.mapper.BaiduAppInfoMapper;
import com.dandinglong.mapper.BaiduAppProcessTimesMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.model.baidu.AipOcrAdopter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AipOcrClientSelector {
    @Autowired
    private BaiduAppInfoMapper baiduAppInfoMapper;
    @Autowired
    private BaiduAppProcessTimesMapper baiduAppProcessTimesMapper;
    private List<AipOcrAdopter> aipClientList=new ArrayList<>();
    @Value("${baidu.invoice.maxNum}")
    private int invoiceMaxNum;
    private volatile boolean initFlag=false;

    public synchronized void init(){
        if(initFlag){
            return;
        }
        Example example=new Example(BaiduAppInfoEntity.class);
        example.createCriteria().andLessThan("invoiceUsedNum",invoiceMaxNum);
        List<BaiduAppInfoEntity> baiduAppInfoEntities = baiduAppInfoMapper.selectByExample(example);
        Iterator<BaiduAppInfoEntity> iterator = baiduAppInfoEntities.iterator();
        while (iterator.hasNext()){
            BaiduAppInfoEntity next = iterator.next();
            AipOcr aipOcr=new AipOcr(next.getAppId(),next.getAppKey(),next.getSecretKey());
            AipOcrAdopter aipOcrAdopter=new AipOcrAdopter(next,aipOcr);
            aipClientList.add(aipOcrAdopter);
        }
        initFlag=true;
    }

    public AipOcr getAvailableInvoiceClient(int aipClientListIndex){
        init();
        aipClientListIndex=aipClientListIndex%aipClientList.size();
        int size=aipClientList.size();
        for(int i=0;i<size;i++){
            BaiduAppInfoEntity baiduAppInfoEntity = aipClientList.get(aipClientListIndex).getBaiduAppInfoEntity();
            baiduAppInfoEntity.setInvoiceUsedNum(invoiceMaxNum);
            if(baiduAppInfoMapper.invoiceUsedNumAdd(baiduAppInfoEntity)==1){
                return aipClientList.get(aipClientListIndex).getAipOcr();
            }else {
                aipClientList.remove(aipClientListIndex);
            }
        }
        throw new NoBaiduAppCanUseException("没有可用的appId");
    }

    /**
     * 一个自增的数字，取余后获取到可用的client
     * @param id
     * @return
     */
    @Transactional
    public int getProcessTimes(int id){
        BaiduAppProcessTimesEntity baiduAppProcessTimesEntity=new BaiduAppProcessTimesEntity();
        baiduAppProcessTimesEntity.setId(id);
        baiduAppProcessTimesEntity=baiduAppProcessTimesMapper.lockProcessTimes(baiduAppProcessTimesEntity);
        baiduAppProcessTimesMapper.processTimesAdd(id);
        return baiduAppProcessTimesEntity.getProcessTimes();
    }

    /**
     * 每日使用次数清零
     */
    public int usedTimesZeroing(){
        return baiduAppInfoMapper.usedNumZeroing();
    }
}
