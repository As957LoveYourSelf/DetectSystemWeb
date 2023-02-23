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
@TableName("faces")
@Accessors(chain = true)
/**
 *  人脸识别数据表
 * */
public class Faces {
    @TableId(type = IdType.ASSIGN_UUID)
    private String fid;
    private String uid;
    private byte[] faceImg;
}
