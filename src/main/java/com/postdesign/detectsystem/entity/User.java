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
    private int uage;
    private String uphone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Byte state;
    private String rcode;
    private Byte online;
    private String registerTime;
    private String utype;
    private String fid;
}