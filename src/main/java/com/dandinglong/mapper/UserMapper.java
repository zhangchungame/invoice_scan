package com.dandinglong.mapper;

import com.dandinglong.entity.UserEntity;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<UserEntity> {
    public int updateLastLoginTime(UserEntity userEntity);
    public int consumScore(int usedScore,int userId);
}
