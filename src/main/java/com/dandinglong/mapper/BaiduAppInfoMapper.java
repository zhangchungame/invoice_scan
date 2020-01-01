package com.dandinglong.mapper;

import com.dandinglong.entity.BaiduAppInfoEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BaiduAppInfoMapper extends Mapper<BaiduAppInfoEntity> {
    public int invoiceUsedNumAdd(BaiduAppInfoEntity baiduAppInfoEntity);
}
