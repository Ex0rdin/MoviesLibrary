package ua.exordin.movies.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ModelAndView handle(MethodArgumentNotValidException manve) {

            ModelAndView model = new ModelAndView("error/method_argument_invalid");
            model.addObject("Error message", manve.getMessage());
            model.addObject("Caused", "Invalid message argument");

            return model;
    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView handle(ConstraintViolationException cve) {

        ModelAndView model = new ModelAndView("error/constraint_violation");
        model.addObject("");

        return model;

    }


}
