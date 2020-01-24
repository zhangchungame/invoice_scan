package com.dandinglong.service;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.exception.*;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.model.*;
import com.dandinglong.util.DateFormaterUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScanProgramService {
    private Logger logger = LoggerFactory.getLogger(ScanProgramService.class);
    @Value("${scanxiaochengxu.appId}")
    private String appId;
    @Value("${scanxiaochengxu.secret}")
    private String secret;
    @Value("${user.freeScore}")
    private int freeScore;

    @Autowired
    private ExcelService excelService;
    @Autowired
    private ImageDealService imageDealService;
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;
    @Autowired
    private UserDetailProcessorService userDetailProcessorService;
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;
    @Autowired
    private AsyncService asyncService;





    /**
     * 添加图片记录，新建压缩、识别客户端到线程池
     *
     * @param filePath
     * @param fileName
     * @param userEntity
     * @return
     */
    public String dealUploadImage(String filePath, String fileName, UserEntity userEntity) {
        if (!userDetailProcessorService.divAndCheckScore(userEntity.getId(), "invoice")) {
            throw new UserScoreNotEnoughException("您的今日免费积分不足");
        }
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        uploadFileEntity.setFileName(fileName);
        uploadFileEntity.setFilePath(filePath);
        uploadFileEntity.setUserId(userEntity.getId());
        uploadFileEntity.setStep(0);
        uploadFileEntity.setInsertTime(new Date());
        uploadFileEntity.setUpdateTime(new Date());
        uploadFileMapper.insert(uploadFileEntity);
        imageDealService.addUploadNum(userEntity);
        ImageRecognition imageRecognition = new ImageRecognitionBaidu(aipOcrClientSelector.getAvailableInvoiceClient(aipOcrClientSelector.getProcessTimes(1)));
        asyncService.uploadFileDealTask(imageRecognition, uploadFileEntity);
//        UploadFileDealRunnable uploadFileDealRunnable=new UploadFileDealRunnable(imageRecognition, uploadFileEntity,imageCompress);
//        threadPoolExecutor.execute(uploadFileDealRunnable);
        return "ok";
    }

    /**
     * 处理前端上传到七牛的图片，调用百度url识别图片
     *
     * @param imageUrl
     * @param userEntity
     * @return
     */
    public String dealUploadImageFromQiniu(String imageUrl, String imageType, UserEntity userEntity) {
        if (!userDetailProcessorService.divAndCheckScore(userEntity.getId(), "invoice")) {
            throw new UserScoreNotEnoughException("您的今日免费积分不足");
        }
        UploadFileEntity uploadFileEntity = new UploadFileEntity();
        uploadFileEntity.setUpdateTime(new Date());
        uploadFileEntity.setStep(0);
        uploadFileEntity.setInsertTime(new Date());
        uploadFileEntity.setUserId(userEntity.getId());
        uploadFileEntity.setFileName(imageUrl);
        uploadFileEntity.setFilePath("");
        uploadFileMapper.insert(uploadFileEntity);

        imageDealService.addUploadNum(userEntity);
        ImageRecognition imageRecognition = new ImageRecognitionBaidu(aipOcrClientSelector.getAvailableInvoiceClient(aipOcrClientSelector.getProcessTimes(1)));
        asyncService.uploadFileDealQiniuTask(imageRecognition, uploadFileEntity);
        return "ok";
    }

    public PageInfo<ScanImageByDayDetailEntity> getScanEntityList(int userId, int pageNum) {
        Example example = new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("userId", userId);
        example.orderBy("dealDate").desc();
        PageHelper.startPage(pageNum, 10);
        PageInfo<ScanImageByDayDetailEntity> pageinfo = new PageInfo<>(scanImageByDayDetailMapper.selectByExample(example));
        return pageinfo;
    }

    public Object reGenerateExcel(int batchId, int userId) throws ParseException {
        ScanImageByDayDetailEntity scanImageByDayDetailEntity = scanImageByDayDetailMapper.selectByPrimaryKey(batchId);
        if (userId != scanImageByDayDetailEntity.getUserId()) {
            throw new StartingGenerateExcelException("非正确用户请求");
        }
        if (!scanImageByDayDetailEntity.getDealDate().equals(DateFormaterUtil.YMDformater.get().format(new Date()))) {
            throw new StartingGenerateExcelException("只能重新更新当天的文件");
        }
        if (scanImageByDayDetailEntity.getExcelStep() != 2) {
            throw new StartingGenerateExcelException("生成状态不正确，请勿重复点击");
        }
        scanImageByDayDetailEntity.setExcelStep(0);
        scanImageByDayDetailEntity.setUpdateTime(new Date());
        scanImageByDayDetailMapper.updateByPrimaryKey(scanImageByDayDetailEntity);
        return selectBatchTypeAndProcess(batchId);
    }

    public Object selectBatchTypeAndProcess(int batchId) throws ParseException {
       return null;
    }

    /**
     * 获取第一条batch记录的处理状态
     */
    public ScanImageByDayDetailEntity firstBatchDetail(int userId, int batchId) {
        ScanImageByDayDetailEntity scanImageByDayDetailEntity = scanImageByDayDetailMapper.selectByPrimaryKey(batchId);
        if (scanImageByDayDetailEntity.getUserId() != userId) {
            throw new BacthProcessException("处理批次用户不正确");
        }
        return scanImageByDayDetailEntity;
    }
}