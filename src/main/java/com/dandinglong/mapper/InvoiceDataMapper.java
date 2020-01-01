package com.dandinglong.mapper;

import com.dandinglong.entity.scanres.InvoiceDataEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface InvoiceDataMapper extends Mapper<InvoiceDataEntity> {
}
