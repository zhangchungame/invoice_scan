package com.dandinglong.service;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.util.DateFormaterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageDealService {
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;
    public String dealImage(){
        return "OK";
    }
    public ScanImageByDayDetailEntity addUploadNum(UserEntity userEntity){
        ScanImageByDayDetailEntity scanImageByDayDetailEntity=new ScanImageByDayDetailEntity();
        scanImageByDayDetailEntity.setDealDate(DateFormaterUtil.YMDformater.get().format(new Date()));
        scanImageByDayDetailEntity.setUserId(userEntity.getId());
        scanImageByDayDetailEntity.setUpdateTime(new Date());
        if(scanImageByDayDetailMapper.uploadNumAdd(scanImageByDayDetailEntity)==0){
            scanImageByDayDetailEntity.setInsertTime(new Date());
            scanImageByDayDetailEntity.setUploadNum(1);
            scanImageByDayDetailEntity.setScanNum(0);
            scanImageByDayDetailEntity.setExcelStep(0);
            try{
                scanImageByDayDetailMapper.insert(scanImageByDayDetailEntity);
            }catch (DuplicateKeyException e){
                scanImageByDayDetailMapper.uploadNumAdd(scanImageByDayDetailEntity);
            }catch (Exception e){
                throw e;
            }
        }
        return scanImageByDayDetailEntity;
    }
}
