package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
/**
 *  学生对应公共课数据表
 * */
@Data
@NoArgsConstructor
@Validated
@TableName("study_public_course")
@Accessors(chain = true)
public class StudyPublicCourse {
    private String sno;
    private Integer cpno;
}
