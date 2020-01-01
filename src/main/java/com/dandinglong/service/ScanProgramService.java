package com.dandinglong.service;

import com.alibaba.fastjson.JSONObject;
import com.dandinglong.entity.Code2Session;
import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.exception.MiniProgreamLoginException;
import com.dandinglong.exception.MultipyUserException;
import com.dandinglong.mapper.UploadFileMapper;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.model.*;
import com.dandinglong.task.UploadFileDealRunnable;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ScanProgramService {
    @Value("${scanxiaochengxu.appId}")
    private String appId;
    @Value("${scanxiaochengxu.secret}")
    private String secret;
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
        System.out.println(res);
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
        UploadFileDealRunnable uploadFileDealRunnable=new UploadFileDealRunnable(imageRecognition, uploadFileEntity,imageCompress);
        threadPoolExecutor.execute(uploadFileDealRunnable);
        return "ok";
    }


}
