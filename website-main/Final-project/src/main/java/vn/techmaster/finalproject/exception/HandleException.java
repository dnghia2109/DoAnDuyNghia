package vn.techmaster.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


    @RestControllerAdvice
    public class HandleException {
        // Xử lý NotFound
        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException exception){
            ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Xử BadRequest
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException exception){
            ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        // Xử lý các Exception còn lại
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorMessage> handleOtherException(RuntimeException exception){
            ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


