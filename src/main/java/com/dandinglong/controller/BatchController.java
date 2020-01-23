package com.dandinglong.controller;

import com.dandinglong.entity.UserEntity;
import com.dandinglong.service.BatchProcessService;
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

@RestController
public class BatchController extends BaseController{
    private Logger logger= LoggerFactory.getLogger(BatchController.class);

    @Autowired
    private BatchProcessService batchProcessService;

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

    @RequestMapping("/batch/getDetailListByTotal")
    public JsonResult getDetailListByTotal(@RequestParam("totalId") int totalId){
        UserEntity userEntity = getUserEntity();
        return ResultUtil.success(batchProcessService.getDetailListByTotal(totalId,userEntity.getId()));
    }
}
