package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

/**
 *  学生数据表
 * */
@Data
@NoArgsConstructor
@Validated
@TableName("students")
@Accessors(chain = true)
public class Student {
    @TableId(type = IdType.INPUT)
    private String sno;
    private String sname;
    private String college;
    private String cls;
    private Integer grade;
    private String major;
    private String sex;

}
