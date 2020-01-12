package com.dandinglong.mapper;

import com.dandinglong.entity.BaiduAppInfoEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BaiduAppInfoMapper extends Mapper<BaiduAppInfoEntity> {
    public int invoiceUsedNumAdd(BaiduAppInfoEntity baiduAppInfoEntity);
    /**
     * 每天使用次数归零
     */
    public int usedNumZeroing();
}
