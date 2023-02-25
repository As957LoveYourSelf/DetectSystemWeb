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
@TableName("course_public")
@Accessors(chain = true)
/**
 *  公共课数据表
 * */
public class PublicCourse {
    private String cname;
    @TableId(type = IdType.AUTO)
    private Integer cno;
    private String college;
    private Integer grade;
}
