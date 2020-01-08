package com.dandinglong.service;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.exception.StartingGenerateExcelException;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.model.excel.GenerateExceInvoice;
import com.dandinglong.task.ExcelGenerateRunnable;
import com.dandinglong.util.DateFormaterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ExcelDealService {
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private AsyncService asyncService;
    /**
     * 開始处理excel，设置今日生成状态1生成中，并放入线程池
     * @param userId
     * @param dealDate
     * @return
     */
    public boolean generateExcelStarting(int userId,String dealDate){
        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("userId",userId)
                .andEqualTo("dealDate",dealDate);
        List<ScanImageByDayDetailEntity> scanImageByDayDetailEntities = scanImageByDayDetailMapper.selectByExample(example);
        if(scanImageByDayDetailEntities.size()!=1){
            throw new StartingGenerateExcelException("没有找到今日数据");
        }
        ScanImageByDayDetailEntity scanImageEntity=scanImageByDayDetailEntities.get(0);
        if(scanImageEntity.getUploadNum()!=scanImageEntity.getScanNum()){
            throw new StartingGenerateExcelException("图片还没有识别完");
        }
        if(scanImageEntity.getExcelStep()==1){
            throw new StartingGenerateExcelException("您的excel文件正在排队生成，请稍后查看");
        }
        if(scanImageEntity.getExcelStep()==3){
            throw new StartingGenerateExcelException("非今日文件不能重新生成");
        }
        scanImageEntity.setExcelStep(1);
        scanImageByDayDetailMapper.updateByPrimaryKey(scanImageEntity);
        asyncService.excelGenerateTask(scanImageEntity);
//        ExcelGenerateRunnable excelGenerateRunnable=new ExcelGenerateRunnable(scanImageEntity);
//        threadPoolExecutor.execute(excelGenerateRunnable);
        return  true;
    }

    /**
     * 获取excel地址和本次出错需要处理的文件
     * @return
     */
    public Map<String,Object> excelDetail(int userId,String dealDate) throws ParseException {
        Map<String,Object> resuslt=new HashMap<>();
        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("userId",userId)
                .andEqualTo("dealDate",dealDate);
        List<ScanImageByDayDetailEntity> scanImageByDayDetailEntities = scanImageByDayDetailMapper.selectByExample(example);
        if(scanImageByDayDetailEntities.size()!=1){
            throw new StartingGenerateExcelException("没有找到今日数据");
        }
        ScanImageByDayDetailEntity scanImageEntity=scanImageByDayDetailEntities.get(0);
        if(scanImageEntity.getExcelStep()<2){
            throw new StartingGenerateExcelException("文件未生成，请重试");
        }
        resuslt.put("excel",scanImageEntity);
        Example example1=new Example(UploadFileEntity.class);
        Date startDate= DateFormaterUtil.formater.get().parse(dealDate+"000000");
        Date endDate=DateFormaterUtil.formater.get().parse(dealDate+"235959");
        example1.createCriteria().andEqualTo("userId",userId).andBetween("insertTime",startDate,endDate).andEqualTo("step",3);
        example1.orderBy("insertTime").desc();
        List<UploadFileEntity> uploadFileEntities = uploadFileMapper.selectByExample(example1);
        resuslt.put("failFiles",uploadFileEntities);
        return resuslt;
    }


}
