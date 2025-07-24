package com.josegvasconcelos.videometadata.application.web.handler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.josegvasconcelos.videometadata.application.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.application.exception.UserNotFoundException;
import com.josegvasconcelos.videometadata.application.web.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({
            InvalidCredentialsException.class,
            JWTCreationException.class,
            JWTVerificationException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(Exception ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(Exception ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
    })
    public ResponseEntity<ErrorResponseDTO> handleForbiddenException(Exception ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
    }
}
