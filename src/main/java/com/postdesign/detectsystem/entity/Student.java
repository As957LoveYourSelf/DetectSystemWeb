package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
@TableName("students")
@Accessors(chain = true)
public class Student {
    @TableId(type = IdType.INPUT)
    private String sno;
    private String sname;
    private String collage;
    private String cls;
    private Integer grade;
    private String major;
    private String sex;

}
