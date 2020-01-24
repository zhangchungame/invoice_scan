package com.dandinglong.mapper;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Repository
public interface ScanImageByDayDetailMapper extends Mapper<ScanImageByDayDetailEntity> {
    public int uploadNumAdd(ScanImageByDayDetailEntity scanImageByDayDetailEntity);
    public int scanNumAdd(ScanImageByDayDetailEntity scanImageByDayDetailEntity);
    public int failNumAdd(ScanImageByDayDetailEntity scanImageByDayDetailEntity);
    public int failNumDiv(@Param("dealDate")String dealDate, @Param("userId")int userId, @Param("type")String type, @Param("updateTime")Date updateTime);
}
