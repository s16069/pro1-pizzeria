package pro.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pro.backend.dto.ErrorDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT,reason = "naruszono spójność danych")  // 409
    public void handleConflict() {

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ResponseBody
    public ErrorDto handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException ex) {
        logger.error("ConstraintViolationException handled", ex);

        String allErrors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return new ErrorDto(HttpStatus.BAD_REQUEST, allErrors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleValidationExceptions(HttpServletRequest req, MethodArgumentNotValidException ex) {

        logger.error("MethodArgumentNotValidException handled", ex);

        String allErrors = ex.getBindingResult().getAllErrors().stream()
                .map((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.joining("; "));

        return new ErrorDto(HttpStatus.BAD_REQUEST, allErrors);
    }
}
