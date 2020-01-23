package com.dandinglong.mapper;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import com.dandinglong.entity.ScanImageByDayTotalEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ScanImageByDayTotalMapper extends Mapper<ScanImageByDayTotalEntity> {
    public int uploadNumAdd(ScanImageByDayTotalEntity scanImageByDayTotalEntity);
    public int scanNumAdd(ScanImageByDayTotalEntity scanImageByDayTotalEntity);
    public int failNumAdd(ScanImageByDayTotalEntity scanImageByDayTotalEntity);
}
