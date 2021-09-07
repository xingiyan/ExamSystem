package com.lwx.mapper;

import com.lwx.bean.TestnewsBen;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/15 15:24
 * @desc: 试卷dao接口
 */
@Repository
public interface TestMapper {
    public int newtest(@Param("test") TestnewsBen testnewsBen,@Param("url") String URL);
    public String queryTest(@Param("testID") String testID);
}
