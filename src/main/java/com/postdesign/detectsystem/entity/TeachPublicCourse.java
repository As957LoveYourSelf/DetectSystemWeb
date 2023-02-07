package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
/**
 *  教师公共课上课数据表
 * */
@Data
@NoArgsConstructor
@Validated
@TableName("teach_public_course")
@Accessors(chain = true)
public class TeachPublicCourse {
    private String tno;
    private Integer cpno;
}

