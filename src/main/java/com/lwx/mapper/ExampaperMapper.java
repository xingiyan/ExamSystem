package com.lwx.mapper;

import com.lwx.bean.StudentBen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/15 16:14
 * @desc:   考生dao接口
 */
@Repository
public interface ExampaperMapper {
    public int newstudent(StudentBen studentBen);
    public StudentBen selectStudent(StudentBen studentBen);
    public StudentBen Student(@Param("studentID") String studentID);
    public List<StudentBen> selectStudenment(@Param("TID") String TID);
}
