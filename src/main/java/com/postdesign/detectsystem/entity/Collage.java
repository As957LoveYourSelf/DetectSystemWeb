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
@TableName("collage")
@Accessors(chain = true)
public class Collage {
    @TableId(type = IdType.INPUT)
    private String name;


}