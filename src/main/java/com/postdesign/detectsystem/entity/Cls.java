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
@TableName("classes")
@Accessors(chain = true)
public class Cls {
    @TableId(type = IdType.INPUT)
    private String className;
    private Integer teacherCount;
    private Integer studentCount;
    private String collage;
    private Integer grade;
    private String major;
    private String headmaster;
}
