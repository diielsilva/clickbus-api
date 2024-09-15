package com.diel.dev.clickbus.shared.handlers;

import com.diel.dev.clickbus.shared.dtos.ErrorResponse;
import com.diel.dev.clickbus.shared.exceptions.ConstraintConflictException;
import com.diel.dev.clickbus.shared.exceptions.EntityNotFoundException;
import com.diel.dev.clickbus.shared.exceptions.QueryParameterNotSpecifiedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException exception,
            HttpServletRequest request
    ) {
        ErrorResponse responseBody = new ErrorResponse(
                OffsetDateTime.now(),
                404,
                request.getServletPath(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(NOT_FOUND)
                .body(responseBody);
    }

    @ExceptionHandler(value = ConstraintConflictException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintConflict(
            ConstraintConflictException exception,
            HttpServletRequest request
    ) {
        ErrorResponse responseBody = new ErrorResponse(
                OffsetDateTime.now(),
                409,
                request.getServletPath(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(CONFLICT)
                .body(responseBody);
    }

    @ExceptionHandler(value = QueryParameterNotSpecifiedException.class)
    protected ResponseEntity<ErrorResponse> handleQueryParametersNotSpecified(
            QueryParameterNotSpecifiedException exception,
            HttpServletRequest request
    ) {
        ErrorResponse responseBody = new ErrorResponse(
                OffsetDateTime.now(),
                400,
                request.getServletPath(),
                exception.getMessage()
        );

        return ResponseEntity
                .badRequest()
                .body(responseBody);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(HttpServletRequest request) {
        ErrorResponse responseBody = new ErrorResponse(
                OffsetDateTime.now(),
                400,
                request.getServletPath(),
                "Invalid request body, please verify your request body"
        );

        return ResponseEntity
                .badRequest()
                .body(responseBody);
    }

}
