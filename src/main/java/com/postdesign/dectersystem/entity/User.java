package com.postdesign.dectersystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@NoArgsConstructor
@Validated
@TableName("users")
@Component
public class User {
    @TableId(type = IdType.ASSIGN_UUID)
    private int id;
    private String uno;
    private String uname;
    private String email;
    private String password;
    private int uage;
    private String uphone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime;
}
