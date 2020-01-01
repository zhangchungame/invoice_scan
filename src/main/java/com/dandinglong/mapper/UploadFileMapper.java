package com.dandinglong.mapper;

import com.dandinglong.entity.UploadFileEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UploadFileMapper extends Mapper<UploadFileEntity> {
}
