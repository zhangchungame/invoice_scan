package com.dandinglong.service;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.exception.StartingGenerateExcelException;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ExcelDealService {
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;

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
        return  true;


    }
}
