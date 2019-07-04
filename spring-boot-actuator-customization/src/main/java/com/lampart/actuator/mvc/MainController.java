package com.lampart.actuator.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/user")
    public String getSomething() {
        return "index";
    }

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }
}
