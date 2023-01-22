package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
@TableName("course")
@Accessors(chain = true)
public class Course {
    private String cname;
    @TableId(type = IdType.ASSIGN_UUID)
    private String cno;
    private String teacher;
    private String major;
    private String collage;
}
