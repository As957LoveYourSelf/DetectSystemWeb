package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

/**
 *  公共课上课地点数据表
 * */

@Data
@NoArgsConstructor
@Validated
@TableName("course_address_public")
@Accessors(chain = true)
public class PublicCourseAddress {
    private String classname;
    private Integer publicCourseNo;
    private String address;
    private String time;
}
