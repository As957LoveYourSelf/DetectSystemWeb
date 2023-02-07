package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
@TableName("course_address_person")
@Accessors(chain = true)
/**
 *  个人专业课上课地点数据表
 * */
public class MajorCourseAddress {
    private String classname;
    private Integer personCourseNo;
    private String address;
    private String time;
}
