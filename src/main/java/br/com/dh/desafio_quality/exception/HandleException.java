package br.com.dh.desafio_quality.exception;

import br.com.dh.desafio_quality.enums.ExceptionType;
import br.com.dh.desafio_quality.enums.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandleException {
    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerNotFoundException(NotFoundException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title(ExceptionType.OBJECT_NOT_FOUND.message)
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (InvalidParamException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidParamException(InvalidParamException ex) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .title(ExceptionType.PARAMETER_NOT_VALID.message)
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
