package com.postdesign.dectersystem.utils;

import com.google.gson.Gson;

import java.util.Objects;

public final class JsonUtil<T> {

    public JsonUtil(){

    }

    public String ObjToJson(T data){
        if (data == null){
            return "";
        }
        try {
            Gson gson = new Gson();
            return gson.toJson(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public <T> T JsonToObj(String json, Class<T> cls){
        Gson gson = new Gson();
        if (Objects.isNull(json))
            return null;
        T obj = gson.fromJson(json, cls);
        if (Objects.isNull(obj)) {
            return null;
        } else {
            return obj;
        }
    }
}
