package com.dandinglong.service;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.StartingGenerateExcelException;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.service.exceldeal.ExcelDealService;
import com.dandinglong.util.ApplicationContextProvider;
import com.dandinglong.util.DateFormaterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private AsyncService asyncService;

    public boolean generateExcelStarting(int deatilId){
        ScanImageByDayDetailEntity scanImageByDayDetailEntity = scanImageByDayDetailMapper.selectByPrimaryKey(deatilId);
        String type = scanImageByDayDetailEntity.getType();
        String excelServiceNmae = ImgTypeEnum.getEnumByType(type).getExcelServiceNmae();
        ExcelDealService excelService =(ExcelDealService)ApplicationContextProvider.getBean(excelServiceNmae);
        excelService.deal(deatilId);
        return true;
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
