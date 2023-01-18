package com.postdesign.detectsystem.service.currencyService;

import java.util.Map;

public interface RegisterService {
    Map<String, Object> register(String uno, String uname, String psd, String email, String type);
    public Map<String, Object> checkCode(String uname, String code);
}
