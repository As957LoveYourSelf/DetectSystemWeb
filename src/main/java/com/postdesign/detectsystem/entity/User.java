package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
/**
 *  用户数据表
 * */
@Data
@NoArgsConstructor
@Validated
@TableName("users")
@Accessors(chain = true)
public class User {
    @TableId(type = IdType.INPUT)
    private String uid;
    private String uname;
    private String email;
    private String password;
    private Integer uage;
    private String uphone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Byte state;
    private String rcode;
    private Byte online;
    private String utype;
    private String fid;
    private String sex;
    private String introduce;
}
