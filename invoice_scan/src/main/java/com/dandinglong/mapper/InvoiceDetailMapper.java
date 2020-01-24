package com.dandinglong.mapper;

import com.dandinglong.entity.scanres.InvoiceDetailEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface InvoiceDetailMapper extends Mapper<InvoiceDetailEntity> {
}
