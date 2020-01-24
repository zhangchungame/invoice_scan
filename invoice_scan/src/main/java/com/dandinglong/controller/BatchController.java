package com.dandinglong.controller;

import com.dandinglong.entity.UploadFileEntity;
import com.dandinglong.entity.UserEntity;
import com.dandinglong.service.BatchProcessService;
import com.dandinglong.service.ExcelService;
import com.dandinglong.util.JsonResult;
import com.dandinglong.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

@RestController
public class BatchController extends BaseController{
    private Logger logger= LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private BatchProcessService batchProcessService;
    @Autowired
    private ExcelService excelService;

    @RequestMapping("/batch/batchTotalList")
    public JsonResult batchTotalList(@RequestParam(name = "pageNum" ,defaultValue = "1")int pageNum){
        UserEntity userEntity=getUserEntity();
        logger.info("batchTotalList pageNum={} userId={}",pageNum,userEntity.getId());
        PageInfo batchTotal = batchProcessService.getBatchTotal(userEntity.getId(), pageNum);
        return ResultUtil.success(batchTotal);
    }
    /**
     * 批次页面刷新第一条的处理结果
     *
     * @return
     */
    @RequestMapping("/batch/firstBatch")
    public JsonResult firstBatch(@RequestParam(value = "batchId") int batchId) {
        UserEntity userEntity = getUserEntity();
        return ResultUtil.success(batchProcessService.firstBatchDetail(userEntity.getId(), batchId));
    }

    /**
     * 根据totalId获取所以的type 的批次详情，处理结果页面用
     * @param totalId
     * @return
     */
    @RequestMapping("/batch/getDetailListByTotal")
    public JsonResult getDetailListByTotal(@RequestParam("totalId") int totalId){
        UserEntity userEntity = getUserEntity();
        return ResultUtil.success(batchProcessService.getDetailListByTotal(totalId,userEntity.getId()));
    }

    @RequestMapping("/batch/failImageList")
    public JsonResult failImageList(@RequestParam("detailId")int detailId) throws ParseException {
        logger.info("请求失败图片列表{}",detailId);
        UserEntity userEntity=getUserEntity();
        List<UploadFileEntity> uploadFileEntities = batchProcessService.failImageList(detailId, userEntity.getId());
        Iterator<UploadFileEntity> iterator = uploadFileEntities.iterator();
        List<Map<String,String>> result=new ArrayList<>();
        while (iterator.hasNext()){
            UploadFileEntity next = iterator.next();
            Map<String,String> map=new HashMap<>();
            map.put("src",next.getFileName());
            map.put("id",String.valueOf(next.getId()));
            result.add(map);
        }
        return ResultUtil.success(result);
    }

    @RequestMapping("/batch/generateExcel")
    public JsonResult generateExcel(@RequestParam("detailId")int detailId){
        return ResultUtil.success(excelService.generateExcelStarting(detailId));
    }
}
