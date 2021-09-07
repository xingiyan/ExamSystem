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
public interface answerMapper {
    public String selectanswer(@Param("TID") String TID,@Param("EID")String EID);
    public int insertanswer(@Param("TID") String TID,@Param("EID")String EID,@Param("answer")String answer);
    public int updateanswer(@Param("TID") String TID,@Param("EID")String EID,@Param("answer")String answer);
}
