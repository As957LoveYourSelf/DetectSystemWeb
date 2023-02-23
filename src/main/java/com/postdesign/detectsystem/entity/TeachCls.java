package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
/**
 *  老师教学班级数据表
 * */
@Data
@NoArgsConstructor
@Validated
@TableName("teachCls")
@Accessors(chain = true)
public class TeachCls {
    private String tno;
    private String classname;

}
