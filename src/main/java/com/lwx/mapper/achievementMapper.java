package com.lwx.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/25 6:54
 * @desc:
 */
@Repository
public interface achievementMapper {
    public String selectachievement(@Param("TID") String TID, @Param("EID") String EID);
    public int insertachievement(@Param("TID") String TID, @Param("EID") String EID, @Param("achievement") String achievement);
    public int updateachievement(@Param("TID") String TID, @Param("EID") String EID, @Param("achievement") String achievement);
}
