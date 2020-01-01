package com.dandinglong.mapper;

import com.dandinglong.entity.ScanImageByDayDetailEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ScanImageByDayDetailMapper extends Mapper<ScanImageByDayDetailEntity> {
    public int uploadNumAdd(ScanImageByDayDetailEntity scanImageByDayDetailEntity);
    public int scanNumAdd(ScanImageByDayDetailEntity scanImageByDayDetailEntity);
}
