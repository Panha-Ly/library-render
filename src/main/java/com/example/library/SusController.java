package com.example.library;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SusController {
    @GetMapping("/sus")
    public String sus(Model model) {
        model.addAttribute("message", "SUS"); 
        model.addAttribute("status", HttpStatus.OK);
        return "error";
    }
}
