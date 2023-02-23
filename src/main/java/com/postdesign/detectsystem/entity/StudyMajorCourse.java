package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
@TableName("study_major_course")
@Accessors(chain = true)
/**
 *  学生对应上课课程数据表
 * */
public class StudyMajorCourse {
    private String sno;
    private Integer cno;
}
