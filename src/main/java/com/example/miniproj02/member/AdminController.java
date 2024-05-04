package com.example.miniproj02.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @RequestMapping("/admin/main")
    public String adminMain() {
        return "admin/main";
    }
}
