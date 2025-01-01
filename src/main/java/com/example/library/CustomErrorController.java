package com.example.library;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

//@RestController
@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController{
    
    @RequestMapping
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute("message", "Resource not found");
            model.addAttribute("status", HttpStatus.NOT_FOUND);
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            model.addAttribute("message", "Internal Server Error");
            model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            model.addAttribute("message", "Bad Request");
            model.addAttribute("status", HttpStatus.BAD_REQUEST);
        }
        return "error";
    }
}
