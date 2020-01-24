package com.dandinglong.mapper;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.ScanImageByDayTotalEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

@Repository
public interface ScanImageByDayTotalMapper extends Mapper<ScanImageByDayTotalEntity> {
    public int uploadNumAdd(ScanImageByDayTotalEntity scanImageByDayTotalEntity);
    public int scanNumAdd(ScanImageByDayTotalEntity scanImageByDayTotalEntity);
    public int failNumAdd(ScanImageByDayTotalEntity scanImageByDayTotalEntity);
    public int failNumDiv(@Param("dealDate")String dealDate, @Param("userId")int userId,@Param("updateTime") Date updaTime);
}
