package com.turingteam.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class ErrorController {
    @RequestMapping("/error/throw")
    public void rethrow(HttpServletRequest request) throws Exception {
        throw ((Exception) request.getAttribute("filter.error"));
    }
}
