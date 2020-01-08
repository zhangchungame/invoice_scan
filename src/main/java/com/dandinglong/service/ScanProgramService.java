package com.dandinglong.service;

import com.alibaba.fastjson.JSONObject;
import com.dandinglong.entity.Code2Session;
import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.exception.MiniProgreamLoginException;
import com.dandinglong.exception.MultipyUserException;
import com.dandinglong.exception.StartingGenerateExcelException;
import com.dandinglong.exception.UserScoreNotEnoughException;
import com.dandinglong.mapper.ScanImageByDayDetailMapper;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.model.*;
import com.dandinglong.task.UploadFileDealRunnable;
import com.dandinglong.util.DateFormaterUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ScanProgramService {
    private Logger logger= LoggerFactory.getLogger(ScanProgramService.class);
    @Value("${scanxiaochengxu.appId}")
    private String appId;
    @Value("${scanxiaochengxu.secret}")
    private String secret;
    @Value("${user.freeScore}")
    private int freeScore;

    @Autowired
    private ExcelDealService excelDealService;
    @Autowired
    private ImageDealService imageDealService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private AipOcrClientSelector aipOcrClientSelector;
    @Autowired
    private UserScoreProcessorService userScoreProcessorService;
    @Autowired
    private ScanImageByDayDetailMapper scanImageByDayDetailMapper;
    @Autowired
    private AsyncService asyncService;

    /**
     * 用户登录，为注册用户注册
     * @param openId
     * @return
     * @throws MultipyUserException
     */
    public UserEntity login(String openId) throws MultipyUserException {
        UserEntity userEntity=null;
        Example example=new Example(UserEntity.class);
        example.createCriteria().andEqualTo("openId",openId);
        List<UserEntity> userEntities = userMapper.selectByExample(example);
        switch (userEntities.size()){
            case 1:
                userEntity=userEntities.get(0);
                userMapper.updateLastLoginTime(userEntity);
                return userEntity;
            case 0:
                userEntity=new UserEntity();
                userEntity.setOpenId(openId);
                userEntity.setTodayUsedScore(freeScore);
                userEntity.setFreeScoreForDay(freeScore);
                userEntity.setRegisterTime(new Date());
                userEntity.setLastLoginTime(new Date());
                userMapper.insert(userEntity);
                return userEntity;
            default:
                throw new MultipyUserException("出现多个相同openId");
        }
    }

    /**
     * 根据登录的jsCode获取openId和session_key
     * @param jsCode
     * @return
     * @throws IOException
     */
    public Code2Session getOpenId(String jsCode) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + jsCode + "&grant_type=authorization_code";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        Response execute = call.execute();
        String res = execute.body().string();
        logger.info(res);
        JSONObject jsonObject = JSONObject.parseObject(res);
        Code2Session code2Session = new Code2Session();
        if (jsonObject.getString("openid") == null) {
            throw new MiniProgreamLoginException("登录失败");
        }
        code2Session.setOpenId(jsonObject.getString("openid"));
        code2Session.setSessionKey(jsonObject.getString("session_key"));
        return code2Session;
    }


    /**
     * 添加图片记录，新建压缩、识别客户端到线程池
     * @param filePath
     * @param fileName
     * @param userEntity
     * @return
     */
    public String dealUploadImage(String filePath,String fileName,UserEntity userEntity){
        if(!userScoreProcessorService.divAndCheckScore(userEntity.getId(),"invoice")){
            throw new UserScoreNotEnoughException("您的今日免费积分不足");
        }
        UploadFileEntity uploadFileEntity=new UploadFileEntity();
        uploadFileEntity.setFileName(fileName);
        uploadFileEntity.setFilePath(filePath);
        uploadFileEntity.setUserId(userEntity.getId());
        uploadFileEntity.setStep(0);
        uploadFileEntity.setInsertTime(new Date());
        uploadFileEntity.setUpdateTime(new Date());
        uploadFileMapper.insert(uploadFileEntity);
        imageDealService.addUploadNum(userEntity);
        ImageRecognition imageRecognition=new ImageRecognitionBaidu(aipOcrClientSelector.getAvailableInvoiceClient(aipOcrClientSelector.getProcessTimes(1)));
        ImageCompress imageCompress=new ImageCompressThumb();
        asyncService.uploadFileDealTask(imageRecognition,uploadFileEntity,imageCompress);
//        UploadFileDealRunnable uploadFileDealRunnable=new UploadFileDealRunnable(imageRecognition, uploadFileEntity,imageCompress);
//        threadPoolExecutor.execute(uploadFileDealRunnable);
        return "ok";
    }

    public PageInfo<ScanImageByDayDetailEntity> getScanEntityList(int userId,int pageNum){
        Example example=new Example(ScanImageByDayDetailEntity.class);
        example.createCriteria().andEqualTo("userId",userId);
        example.orderBy("dealDate").desc();
        PageHelper.startPage(pageNum,10);
        PageInfo<ScanImageByDayDetailEntity> pageinfo=new PageInfo<>(scanImageByDayDetailMapper.selectByExample(example));
        return pageinfo;
    }

    public Object reGenerateExcel(int batchId,int userId) throws ParseException {
        ScanImageByDayDetailEntity scanImageByDayDetailEntity = scanImageByDayDetailMapper.selectByPrimaryKey(batchId);
        if(userId!=scanImageByDayDetailEntity.getUserId()){
            throw new StartingGenerateExcelException("非正确用户请求");
        }
        if(!scanImageByDayDetailEntity.getDealDate().equals(DateFormaterUtil.YMDformater.get().format(new Date()))){
            throw new StartingGenerateExcelException("只能重新更新当天的文件");
        }
        if(scanImageByDayDetailEntity.getExcelStep()!=2){
           throw new StartingGenerateExcelException("生成状态不正确，请勿重复点击");
        }
        scanImageByDayDetailEntity.setExcelStep(0);
        scanImageByDayDetailEntity.setUpdateTime(new Date());
        scanImageByDayDetailMapper.updateByPrimaryKey(scanImageByDayDetailEntity);
        return selectBatchTypeAndProcess(batchId);
    }

    public Object selectBatchTypeAndProcess(int batchId) throws ParseException {
        Map<String,Object> result=new HashMap<>();
        ScanImageByDayDetailEntity scanImageByDayDetailEntity = scanImageByDayDetailMapper.selectByPrimaryKey(batchId);
        if(scanImageByDayDetailEntity.getExcelStep()==0){
            if(excelDealService.generateExcelStarting(scanImageByDayDetailEntity.getUserId(),scanImageByDayDetailEntity.getDealDate())){
                result.put("type","startDeal");
                result.put("msg","开始生成，请稍后刷新重试");
                return result;
            }else{
                throw new StartingGenerateExcelException("生成excel任务出错，请重试");
            }
        }else if(scanImageByDayDetailEntity.getExcelStep()==1){
            result.put("type","isDealing");
            result.put("msg","正在生成队列中，请稍后刷新重试");
            return result;
        }else{
            result.put("type","hasDeal");
            result.put("msg","已生成");
            result.put("data",excelDealService.excelDetail(scanImageByDayDetailEntity.getUserId(),scanImageByDayDetailEntity.getDealDate()));
            return result;
        }
    }
}
