package com.postdesign.detectsystem.service.androidService;

import com.postdesign.detectsystem.entity.User;

import java.util.Map;

public interface UserMangerService {
    Map<String, Object> changeInfo(User user);
    Map<String, Object> changePassword(String uid, String newPassword);

    Map<String, Object> changeConfirm(String uid, String code);
}
