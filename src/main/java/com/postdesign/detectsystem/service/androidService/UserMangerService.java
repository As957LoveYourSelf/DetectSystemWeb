package com.postdesign.detectsystem.service.androidService;

import com.postdesign.detectsystem.entity.User;

import java.util.Map;

public interface UserMangerService {
    Map<String, Object> changeInfo(String uid, Map<String, Object> newInfo);
    Map<String, Object> changePassword(String uid, String newPassword);
    boolean changeConfirm(String uid, String code);

}
