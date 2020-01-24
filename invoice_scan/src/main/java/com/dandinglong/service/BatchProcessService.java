package com.dandinglong.service;


import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.ScanImageByDayTotalEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.BacthProcessException;
import com.dandinglong.exception.WxException;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.mapper.ScanImageByDayTotalMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.util.DateFormaterUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class BatchProcessService {
    private Logger logger = LoggerFactory.getLogger(BatchProcessService.class);
    @Autowired
    private ScanImageByDayTotalMapper scanImageByDayTotalMapper;
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;
    @Autowired
    private UploadFileMapper uploadFileMapper;


    /**
     * 重新上传，失败减一
     */
    @Transactional
    public boolean divFailNum(String dealDate,int userId,String type){
        scanImageByDayDetailMapper.failNumDiv(dealDate,userId,type,new Date());
        scanImageByDayTotalMapper.failNumDiv(dealDate,userId,new Date());
        return true;
    }
    /**
     * 识别失败加一
     * @param dealDate
     * @param userId
     * @param type
     * @return
     */
    @Transactional
    public ScanImageByDayDetailEntity addFailNum(String dealDate,int userId,String type){
        logger.info("addScanNum dealDate={}   userId={}   type={} ",dealDate,userId,type);
        ImgTypeEnum.checkType(type);

        ScanImageByDayDetailEntity entity=null;
        ScanImageByDayTotalEntity totalEntity=null;

        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("dealDate",dealDate)
                .andEqualTo("userId",userId)
                .andEqualTo("type",type);
        List<ScanImageByDayDetailEntity> scanImageByDayDetailEntities = scanImageByDayDetailMapper.selectByExample(example);
        Example exampleTotal=new Example(ScanImageByDayTotalEntity.class);
        exampleTotal.createCriteria().andEqualTo("dealDate",dealDate).andEqualTo("userId",userId);
        List<ScanImageByDayTotalEntity> scanImageByDayTotalEntities = scanImageByDayTotalMapper.selectByExample(exampleTotal);
        switch (scanImageByDayTotalEntities.size()){
            case 1:
                totalEntity=scanImageByDayTotalEntities.get(0);
                totalEntity.setUpdateTime(new Date());
                scanImageByDayTotalMapper.failNumAdd(totalEntity);
                break;
            default:
                throw new WxException("多个相同批次");
        }
        switch (scanImageByDayDetailEntities.size()){
            case 1:
                entity=scanImageByDayDetailEntities.get(0);
                entity.setUpdateTime(new Date());
                scanImageByDayDetailMapper.failNumAdd(entity);
                break;
            default:
                throw new WxException("多个相同批次");
        }
        return  entity;
    }
    /**
     * 识别成功数量加1
     * @param dealDate
     * @param userId
     * @param type
     * @return
     */
    @Transactional
    public ScanImageByDayDetailEntity addScanNum(String dealDate,int userId,String type){
        logger.info("addScanNum dealDate={}   userId={}   type={} ",dealDate,userId,type);
        ImgTypeEnum.checkType(type);

        ScanImageByDayDetailEntity entity=null;
        ScanImageByDayTotalEntity totalEntity=null;

        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("dealDate",dealDate)
                .andEqualTo("userId",userId)
                .andEqualTo("type",type);
        List<ScanImageByDayDetailEntity> scanImageByDayDetailEntities = scanImageByDayDetailMapper.selectByExample(example);
        Example exampleTotal=new Example(ScanImageByDayTotalEntity.class);
        exampleTotal.createCriteria().andEqualTo("dealDate",dealDate).andEqualTo("userId",userId);
        List<ScanImageByDayTotalEntity> scanImageByDayTotalEntities = scanImageByDayTotalMapper.selectByExample(exampleTotal);
        switch (scanImageByDayTotalEntities.size()){
            case 1:
                totalEntity=scanImageByDayTotalEntities.get(0);
                totalEntity.setUpdateTime(new Date());
                scanImageByDayTotalMapper.scanNumAdd(totalEntity);
                break;
            default:
                throw new WxException("多个相同批次");
        }
        switch (scanImageByDayDetailEntities.size()){
            case 1:
                entity=scanImageByDayDetailEntities.get(0);
                entity.setUpdateTime(new Date());
                scanImageByDayDetailMapper.scanNumAdd(entity);
                break;
            default:
                throw new WxException("多个相同批次");
        }
        return  entity;
    }

    /**
     * 上传数加一
     * @param dealDate
     * @param userId
     * @param type
     * @return
     */
    @Transactional
    public ScanImageByDayDetailEntity addUploadNum(String dealDate,int userId,String type){
        logger.info("addUploadNum dealDate={}   userId={}   type={} ",dealDate,userId,type);
        ImgTypeEnum.checkType(type);

        ScanImageByDayDetailEntity entity=null;
        ScanImageByDayTotalEntity totalEntity=null;

        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("dealDate",dealDate)
                .andEqualTo("userId",userId)
                .andEqualTo("type",type);
        List<ScanImageByDayDetailEntity> scanImageByDayDetailEntities = scanImageByDayDetailMapper.selectByExample(example);
        Example exampleTotal=new Example(ScanImageByDayTotalEntity.class);
        exampleTotal.createCriteria().andEqualTo("dealDate",dealDate).andEqualTo("userId",userId);
        List<ScanImageByDayTotalEntity> scanImageByDayTotalEntities = scanImageByDayTotalMapper.selectByExample(exampleTotal);
        switch (scanImageByDayTotalEntities.size()){
            case 0:
                totalEntity=new ScanImageByDayTotalEntity();
                totalEntity.setDealDate(dealDate);
                totalEntity.setUploadNum(1);
                totalEntity.setFailNum(0);
                totalEntity.setScanNum(0);
                totalEntity.setUserId(userId);
                totalEntity.setUpdateTime(new Date());
                totalEntity.setInsertTime(new Date());
                scanImageByDayTotalMapper.insert(totalEntity);
                break;
            case 1:
                totalEntity=scanImageByDayTotalEntities.get(0);
                totalEntity.setUpdateTime(new Date());
                scanImageByDayTotalMapper.uploadNumAdd(totalEntity);
                break;
            default:
                throw new WxException("多个相同批次");
        }
        switch (scanImageByDayDetailEntities.size()){
            case 0:
                entity=new ScanImageByDayDetailEntity();
                entity.setUpdateTime(new Date());
                entity.setInsertTime(new Date());
                entity.setExcelStep(0);
                entity.setExcelFile("");
                entity.setDealDate(dealDate);
                entity.setScanNum(0);
                entity.setFailNum(0);
                entity.setUploadNum(1);
                entity.setType(type);
                entity.setUserId(userId);
                entity.setTotalId(totalEntity.getId());
                scanImageByDayDetailMapper.insert(entity);
                break;
            case 1:
                entity=scanImageByDayDetailEntities.get(0);
                entity.setUpdateTime(new Date());
                scanImageByDayDetailMapper.uploadNumAdd(entity);
                break;
            default:
                throw new WxException("多个相同批次");
        }
        return  entity;
    }


    /**
     * 用户批次列表
     * @param userId
     * @param pageNum
     * @return
     */
    public PageInfo getBatchTotal(int userId,int pageNum){
        Example example=new Example(ScanImageByDayTotalEntity.class);
        example.createCriteria().andEqualTo("userId",userId);
        example.orderBy("id").desc();
        PageHelper.startPage(pageNum, 20);
        PageInfo<ScanImageByDayTotalEntity> pageinfo = new PageInfo<>(scanImageByDayTotalMapper.selectByExample(example));
        return pageinfo;
    }

    /**
     * 获取第一条batch记录的处理状态
     */
    public ScanImageByDayTotalEntity firstBatchDetail(int userId, int batchId) {
        ScanImageByDayTotalEntity scanImageByDayTotalEntity = scanImageByDayTotalMapper.selectByPrimaryKey(batchId);
        if (scanImageByDayTotalEntity.getUserId() != userId) {
            throw new BacthProcessException("处理批次用户不正确");
        }
        return scanImageByDayTotalEntity;
    }

    public List<ScanImageByDayDetailEntity> getDetailListByTotal(int totalId,int userId){
        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("totalId",totalId).andEqualTo("userId",userId);
        example.orderBy("type").desc();
        List<ScanImageByDayDetailEntity> scanImageByDayDetailEntities = scanImageByDayDetailMapper.selectByExample(example);
        Iterator<ScanImageByDayDetailEntity> iterator = scanImageByDayDetailEntities.iterator();
        while (iterator.hasNext()){
            ScanImageByDayDetailEntity next = iterator.next();
            next.setType(ImgTypeEnum.getEnumByType(next.getType()).getName());
        }
        return scanImageByDayDetailEntities;
    }

    /**
     * 根据deailbatchId获取当天的失败图片列表
     */
    public List<UploadFileEntity> failImageList(int detailId,int userId) throws ParseException {
        ScanImageByDayDetailEntity scanImageByDayDetailEntity = scanImageByDayDetailMapper.selectByPrimaryKey(detailId);
        if(scanImageByDayDetailEntity.getUserId()!=userId){
            throw new WxException("用户不正确");
        }
        Example example=new Example(UploadFileEntity.class);
        String startTime=scanImageByDayDetailEntity.getDealDate()+"000000";
        String endTime=scanImageByDayDetailEntity.getDealDate()+"235959";
        Date start = DateFormaterUtil.formater.get().parse(startTime);
        Date end = DateFormaterUtil.formater.get().parse(endTime);
        example.createCriteria().andBetween("insertTime",start,end).andEqualTo("step",3);
        List<UploadFileEntity> uploadFileEntities = uploadFileMapper.selectByExample(example);
        return uploadFileEntities;

    }
}