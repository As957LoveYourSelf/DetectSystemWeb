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
    @MppMultiId
    private String className;
    private String teacherNo;
    private String studentNo;
    @MppMultiId
    private String collage;
    @MppMultiId
    private Integer grade;
    @MppMultiId
    private String major;
    private String useClassName;
}
