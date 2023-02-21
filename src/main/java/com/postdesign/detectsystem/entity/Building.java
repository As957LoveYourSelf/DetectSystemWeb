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
@TableName("building")
@Accessors(chain = true)
/**
 *  教学楼数据表
 * */
public class Building {
    private String bname;
    private Integer floors;
    @TableId(type = IdType.INPUT)
    private String clsNo;
    private Integer isOrder;
}
