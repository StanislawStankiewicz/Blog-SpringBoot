package com.blog.blogspringboot.exceptions;

import com.blog.blogspringboot.model.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BlogpostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBlogpostNotFoundException(BlogpostNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CredentialsException.class)
    public ResponseEntity<ErrorResponse> handleCredentialsException(CredentialsException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal Server Error")
                .trace(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
