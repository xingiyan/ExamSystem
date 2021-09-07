package com.lwx.mapper;

import com.lwx.bean.StudentBen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/15 16:15
 * @desc:   考试记录dao接口
 */
@Repository
public interface RecordMapper {
    public int newrecord(@Param("TID") String TID, @Param("EID") String EID);
    public int updateState(@Param("state") String state,@Param("studentid") String studentid,@Param("tid") String tid);
    public String selectstate(@Param("studentid")String studentid,@Param("tid")String tid);
    public List<StudentBen> selectAllState(@Param("tid")String tid);
}
