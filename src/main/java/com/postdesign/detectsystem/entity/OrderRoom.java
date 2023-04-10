package com.postdesign.detectsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@NoArgsConstructor
@Validated
@TableName("order_room")
@Accessors(chain = true)
public class OrderRoom {
    String clsNo;
    String time;
    String uno;
}
