package com.arcade.FatKidBoot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    public String startMessage() {
        return "THE BACK-END IS WORKING FINE!";
    }

    /* I NEED TO SEE THE CSRF TOKEN */
    @GetMapping("/csrf")
    public CsrfToken getToken(HttpServletRequest request /*To extract token*/) {
        return (CsrfToken) /*THIS IS OBJECT -> CSRF TOKEN*/ request.getAttribute("_csrf");
    }
}
