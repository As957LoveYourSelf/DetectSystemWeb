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
 *  教师数据表
 * */
@Data
@NoArgsConstructor
@Validated
@TableName("teachers")
@Accessors(chain = true)
public class Teacher {
    @TableId(type = IdType.INPUT)
    private String tno;
    private String tname;
    private String college;
    private String title;
    private String leadclass;
    private String sex;
}
