package com.postdesign.detectsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//统一返回前端的json格式
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JSONResult <T>{
    private Integer code;//编码 200 成功 非200失败
    private String message;
    private T data;

    public JSONResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JSONResult(T data) {
        this.data = data;
        this.code = 200;
        this.message = "success";
    }
}
