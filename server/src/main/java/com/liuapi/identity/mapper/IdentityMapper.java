package com.liuapi.identity.mapper;

import com.liuapi.identity.model.domain.IdentityDO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther 柳俊阳
 * @github https://github.com/johnliu1122/
 * @csdn https://blog.csdn.net/qq_35695616
 * @email johnliu1122@163.com
 * @date 2020/8/30
 */
@Mapper
public interface IdentityMapper {

    @Update("update identity_t set max_id = max_id+step where biz_tag=#{bizTag}")
    int update(String bizTag);

    @Results({
            @Result(property = "maxId",column = "max_id"),
            @Result(property = "step",column = "step")
    })
    @Select("select max_id,step from identity_t where biz_tag = #{bizTag}")
    IdentityDO query(String bizTag);

    @Select("select biz_tag from identity_t")
    List<String> getAllBizTags();
}
