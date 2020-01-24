package com.dandinglong.mapper;

import com.dandinglong.entity.BaiduAppProcessTimesEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BaiduAppProcessTimesMapper extends Mapper<BaiduAppProcessTimesEntity> {
    public BaiduAppProcessTimesEntity lockProcessTimes(BaiduAppProcessTimesEntity baiduAppProcessTimesEntity);
    public int processTimesAdd(int id);
}
