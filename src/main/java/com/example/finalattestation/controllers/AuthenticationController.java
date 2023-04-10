package com.example.finalattestation.controllers;

import com.example.finalattestation.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthenticationController {


    @GetMapping("/authentication")
    public String login(){
        return "authentication";
    }




}
