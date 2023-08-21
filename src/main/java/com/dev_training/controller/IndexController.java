package com.dev_training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * インデックスコントローラ。
 */
@Controller
@RequestMapping(value = "/")
public class IndexController
{
    @RequestMapping(value="")
    public String index() {
        return "redirect:/login";
    }
}
