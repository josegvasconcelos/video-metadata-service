package com.josegvasconcelos.videometadata.application.web.handler;

import com.josegvasconcelos.videometadata.domain.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.application.exception.TokenGenerationException;
import com.josegvasconcelos.videometadata.application.exception.TokenValidationException;
import com.josegvasconcelos.videometadata.domain.exception.UserNotFoundException;
import com.josegvasconcelos.videometadata.application.web.dto.response.ErrorResponseDTO;
import com.josegvasconcelos.videometadata.domain.exception.VideoNotFoundException;
import com.josegvasconcelos.videometadata.resource.exception.WrongURLFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({
            InvalidCredentialsException.class,
            TokenGenerationException.class,
            TokenValidationException.class,
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
            NoResourceFoundException.class,
            VideoNotFoundException.class,
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

    @ExceptionHandler({
            WrongURLFormatException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(Exception ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST,
                ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex, HttpServletRequest request) {
        var responseBody = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                request.getRequestURI()
        );
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
