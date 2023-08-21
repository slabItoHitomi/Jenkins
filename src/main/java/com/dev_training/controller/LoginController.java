package com.dev_training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログインコントローラ。
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value="")
    public String login() {
        return "login/loginForm";
    }
}
