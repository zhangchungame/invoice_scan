package com.dandinglong.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dandinglong.entity.Code2Session;
import com.dandinglong.entity.LoginAddScoreEntity;
import com.dandinglong.entity.ScoreAddLogEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.enums.ImgTypeEnum;
import com.dandinglong.exception.MiniProgreamLoginException;
import com.dandinglong.exception.MultipyUserException;
import com.dandinglong.exception.WxException;
import com.dandinglong.mapper.LoginAddScoreMapper;
import com.dandinglong.mapper.ScoreAddLogMapper;
import com.dandinglong.mapper.UserMapper;
import com.dandinglong.model.qiniu.FileSaveSys;
import com.dandinglong.util.DateFormaterUtil;
import com.dandinglong.util.FileNameUtil;
import okhttp3.*;
import okio.BufferedSink;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserDetailProcessorService {
    private Logger logger = LoggerFactory.getLogger(UserDetailProcessorService.class);
    @Autowired
    private UserMapper userMapper;
    @Value("${score.invoice.percommitscore}")
    private int invoicepercommitscore;
    @Value("${scanxiaochengxu.appId}")
    private String appId;
    @Value("${scanxiaochengxu.secret}")
    private String secret;
    @Value("${uploadFileLocation}")
    private String uploadFileLocation;
    @Value("${user.freeScore}")
    private int freeScore;
    @Value("${user.orginalScore}")
    private int orginalScore;
    @Value("${user.loginScore}")
    private int loginScore;
    @Value("${mainFilePath}")
    private String mainFilePath;
    @Autowired
    private ScoreAddLogMapper scoreAddLogMapper;
    @Autowired
    private LoginAddScoreMapper loginAddScoreMapper;

    /**
     * 用户登录，为注册用户注册
     *
     * @param openId
     * @return
     * @throws MultipyUserException
     */
    public UserEntity login(String openId, int orginalUserId) throws MultipyUserException {
        UserEntity userEntity = null;
        Example example = new Example(UserEntity.class);
        example.createCriteria().andEqualTo("openId", openId);
        List<UserEntity> userEntities = userMapper.selectByExample(example);
        switch (userEntities.size()) {
            case 1:
                userEntity = userEntities.get(0);
                userMapper.updateLastLoginTime(userEntity);
                break;
            case 0:
                userEntity = new UserEntity();
                userEntity.setOpenId(openId);
                userEntity.setTodayUsedScore(freeScore);
                userEntity.setFreeScoreForDay(freeScore);
                userEntity.setShowWelcome(1);
                userEntity.setOrginalUserId(0);
                userEntity.setRegisterTime(new Date());
                userEntity.setLastLoginTime(new Date());
                userMapper.insert(userEntity);
                break;
            default:
                throw new MultipyUserException("出现多个相同openId");
        }
        loginAddScore(userEntity);
        addUserScore(userEntity, orginalUserId);
        return userEntity;
    }

    /**
     * 每日登陆赠送积分
     * @param userEntity
     */
    private void loginAddScore(UserEntity userEntity){
        logger.info("开始添加登陆积分userId={}",userEntity.getId());
        LoginAddScoreEntity loginAddScoreEntity=new LoginAddScoreEntity();
        loginAddScoreEntity.setAddScore(loginScore);
        loginAddScoreEntity.setInsertTime(new Date());
        loginAddScoreEntity.setUserId(userEntity.getId());
        loginAddScoreEntity.setLoginDate(DateFormaterUtil.YMDformater.get().format(new Date()));
        try {
            loginAddScoreMapper.insert(loginAddScoreEntity);
            userMapper.orginalUserAddScore(userEntity.getId(),loginScore);
        }catch (DuplicateKeyException e){
            logger.info("今日已登录,不送积分userId={}",userEntity.getId());
        }
    }
    /**
     * 分享添加用户积分
     *
     * @param userEntity
     */
    private void addUserScore(UserEntity userEntity, int orginalUserId) {
        if (userEntity.getOrginalUserId() == 0&&orginalUserId!=0 && userEntity.getId() != orginalUserId) {
            userEntity.setOrginalUserId(orginalUserId);
            UserEntity orginalUserEntity = userMapper.selectByPrimaryKey(orginalUserId);
            if (orginalUserEntity != null) {
                logger.info("开始添加会员积分 来源用户id={}   userEntity={}",orginalUserId,JSON.toJSONString(userEntity));
                ScoreAddLogEntity scoreAddLogEntity = new ScoreAddLogEntity();
                scoreAddLogEntity.setAddScore(orginalScore);
                scoreAddLogEntity.setOrginalUserId(orginalUserId);
                scoreAddLogEntity.setUserId(userEntity.getId());
                scoreAddLogEntity.setInsertTime(new Date());
                scoreAddLogMapper.insert(scoreAddLogEntity);
                userMapper.orginalUserAddScore(orginalUserId, orginalScore);
                userMapper.updateByPrimaryKey(userEntity);
            }
        }
    }

    /**
     * 根据登录的jsCode获取openId和session_key
     *
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
     * 获取用户状态
     */
    public UserEntity userDetail(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 更新用户头像等信息
     *
     * @param userEntity
     * @return
     * @throws IOException
     */
    public UserEntity updateUserInfo(UserEntity userEntity) throws IOException {
        if (StringUtils.isEmpty(userEntity.getAvatarUrl())) {
            throw new WxException("获取用户信息失败");
        }
        long imgUpdatetime;
        if (userEntity.getShareImgUpdateTime() == null) {
            imgUpdatetime = 0;
        } else {
            imgUpdatetime = userEntity.getShareImgUpdateTime().getTime();
        }
        long nowTime = new Date().getTime();
        if ((nowTime - imgUpdatetime) > 3600 * 1000 * 24 * 30L) {
            logger.info("开始更新头像 nowTime={}  oldTime={} user={}", nowTime, imgUpdatetime, JSON.toJSONString(userEntity));
            userEntity.setShareImgUrl(generateUserShareImage(userEntity));
            userEntity.setShareImgUpdateTime(new Date());
        }
        userMapper.updateByPrimaryKey(userEntity);
        return userEntity;
    }

    /**
     * 上传图片后消耗积分
     *
     * @param userId
     * @param type
     * @return
     */
    public boolean divAndCheckScore(int userId, String type) {
        ImgTypeEnum imgTypeEnum=ImgTypeEnum.getEnumByType(type);
        int percommitScore = imgTypeEnum.getConsumScore();
        if (userMapper.consumScore(percommitScore, userId) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 每日恢复用户积分
     */
    public int recoverUserFreeScore() {
        return userMapper.recoverUserFreeScore();
    }

    public String generateUserShareImage(UserEntity userEntity) throws IOException {
        String avatarPathName = downLoadAvatar(userEntity);
        logger.info("下载头像成功 {}", userEntity.getId());
        String qrCondePathName = uploadFileLocation + FileNameUtil.generateFileName("aaa.jpg");
        String outName = FileNameUtil.generateFileName("aaa.jpg");
        String outputPath = uploadFileLocation + outName;
        makeQrCode(qrCondePathName, userEntity.getId());
        logger.info("生成二维码成功 userId={}", userEntity.getId());
        overlapImage(mainFilePath+"share_template.jpg", qrCondePathName, avatarPathName, userEntity.getNickName(), outputPath);
        logger.info("合成图片成功 userId={}", userEntity.getId());
        String uploadFileUrl = FileSaveSys.uploadFile(uploadFileLocation, outName);
        return uploadFileUrl;
    }

    public String downLoadAvatar(UserEntity userEntity) throws IOException {
        if (StringUtils.isEmpty(userEntity.getAvatarUrl())) {
            throw new WxException("没有获取到用户头像");
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(userEntity.getAvatarUrl()).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String outputPath = uploadFileLocation + FileNameUtil.generateFileName("aaa.jpg");
        FileOutputStream fo = new FileOutputStream(outputPath);
        try {
            fo.write(response.body().bytes());
        } finally {
            fo.close();
        }
        return outputPath;
    }

    /**
     * 获取微信二维码
     *
     * @param filePathName
     * @param userId
     * @throws IOException
     */
    public void makeQrCode(String filePathName, int userId) throws IOException {
        String accessToken = accessToken();
        OkHttpClient client = new OkHttpClient();
        Map<String, String> map = new HashMap<>();
        map.put("scene", String.valueOf(userId));
        String s = JSON.toJSONString(map);
        RequestBody requestBody1 = RequestBody.create(s, MediaType.parse("application/json"));
        Request request = new Request.Builder().url("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken)
                .post(requestBody1).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        FileOutputStream fileOutputStream = new FileOutputStream(filePathName);
        ResponseBody body = response.body();
        try {
            fileOutputStream.write(body.bytes());
        } finally {
            fileOutputStream.close();
        }
        logger.info("生成二维码成功" + userId);
    }

    /**
     * 获取微信token
     *
     * @return
     * @throws IOException
     */
    public String accessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, secret)).build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String resp = response.body().string();
        JSONObject jsonObject = JSON.parseObject(resp);
        String token = jsonObject.getString("access_token");
        if (StringUtils.isEmpty(token)) {
            logger.error("微信token error {}", jsonObject.getString("error"));
            throw new WxException("获取token失败");
        }
        logger.info("微信token={}", token);
        return token;
    }

    /**
     * 生成分享图片
     *
     * @param backgroundPath
     * @param qrCodePath
     * @param avatarPath
     * @param message01
     * @param outPutPath
     * @throws IOException
     */
    private void overlapImage(String backgroundPath, String qrCodePath, String avatarPath, String message01, String outPutPath) throws IOException {
        File bk = new File(backgroundPath);
        BufferedImage background = ImageIO.read(bk);
//            BufferedImage qrCode = resizeImage(150,150,ImageIO.read(new File("这里是插入二维码图片的路径！")));
        BufferedImage qrCode = resizeImage(200, 200, ImageIO.read(new File(qrCodePath)));
        BufferedImage avatar = resizeImage(50, 50, ImageIO.read(new File(avatarPath)));
        //在背景图片中添加入需要写入的信息，例如：扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！
        //String message = "扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！";
        Graphics2D g = background.createGraphics();
        g.setColor(Color.ORANGE);
//        g.setFont(new Font("微软雅黑", Font.BOLD, 20));
//        g.drawString(message01, 90, 240);
        //在背景图片上添加二维码图片
        g.drawImage(qrCode, 350, 250, qrCode.getWidth(), qrCode.getHeight(), null);
        g.drawImage(avatar, 30, 200, avatar.getWidth(), avatar.getHeight(), null);
        g.dispose();
//            ImageIO.write(background, "jpg", new File("这里是一个输出图片的路径"));
        ImageIO.write(background, "jpg", new File(outPutPath));
    }

    private BufferedImage resizeImage(int x, int y, BufferedImage bfi) {
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }
}
