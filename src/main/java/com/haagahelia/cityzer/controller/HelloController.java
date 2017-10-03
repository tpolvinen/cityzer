package com.haagahelia.cityzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HelloController {

    @RequestMapping("/hei")
    public String hei() {
        return "Spring sanoo hei!";
    }

}