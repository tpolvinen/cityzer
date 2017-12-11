package com.haagahelia.cityzer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HelloController {

    @Value("${hello.message}")
    private String hellomessage;

    @RequestMapping("/hei")
    public String hei() {
        return "Hellomessage on: " + hellomessage;
    }

}