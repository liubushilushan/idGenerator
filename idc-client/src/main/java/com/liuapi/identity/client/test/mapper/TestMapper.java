package com.liuapi.identity.client.test.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {

    @Insert("INSERT INTO `verify_correct` (`id`,`biz_tag`,`tid`) VALUES (#{id},#{bizTag},#{tid})")
    int insert(@Param("id") Long id,@Param("tid") Long tid,@Param("bizTag") String bizTag);
}
