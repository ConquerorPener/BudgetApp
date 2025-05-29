package com.example.pasir_wojciak_radoslaw.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/api/test")
    public String test(){
            return "Hello world";
    }
    @GetMapping("/api/getInfo")
    public Map<String,String> test2(){
        Map<String,String> appInfo = new HashMap<>();
        appInfo.put("appName", "Aplikacja Budżetowa");
        appInfo.put("version", "1.0");
        appInfo.put("message", "Witaj w aplikacji budżetowej stworzonej ze Spring Boot!");
        return appInfo;

    }

}
