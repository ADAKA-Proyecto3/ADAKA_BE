package com.cenfotec.adaka.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HolaMundoController {
    @GetMapping("/hola")
    public String  getHolaMundo(){
        return "Hola Mundo";
    }

}
