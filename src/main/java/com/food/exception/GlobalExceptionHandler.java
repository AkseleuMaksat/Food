package com.food.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", "Page not found");
        return "error/404";
    }
}
