package com.josegvasconcelos.videometadata.application.web.handler;

import com.josegvasconcelos.videometadata.application.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.application.web.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }
}
