package com.postdesign.dectersystem.controller.android;


import com.postdesign.dectersystem.service.ALoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loginPage")
public class AndroidLoginController {

    ALoginService loginService;

}
