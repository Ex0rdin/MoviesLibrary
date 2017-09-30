package ua.exordin.movies.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

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
        model.addObject("Error message", cve.getMessage());
        model.addObject("Caused", cve.getConstraintViolations().stream().map(Object::toString)
                .collect(Collectors.joining(", ")));

        return model;

    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<String> handle(org.hibernate.exception.ConstraintViolationException cve) {

        StringBuilder sb = new StringBuilder();
        sb.append("Error message: ");
        sb.append(cve.getLocalizedMessage());
        sb.append("\n");
        sb.append("Caused: ");
        sb.append(cve.getSQLException());

        return new ResponseEntity<>(sb.toString(), HttpStatus.CONFLICT);

    }


}
