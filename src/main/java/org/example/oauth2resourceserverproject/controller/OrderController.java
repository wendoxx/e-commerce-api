package org.example.oauth2resourceserverproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @GetMapping("/public")
    public String getById(){
        return "it get any order by id.";
    }


}
