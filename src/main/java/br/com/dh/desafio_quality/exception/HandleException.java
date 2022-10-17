package br.com.dh.desafio_quality.exception;

import br.com.dh.desafio_quality.enums.ExceptionType;
import br.com.dh.desafio_quality.enums.Msg;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Handle exception.
 */
@ControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {
    /**
     * Handler not found exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
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

    /**
     * Handler invalid param exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
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

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders httpHeaders,
            HttpStatus httpStatus,
            WebRequest webRequest) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        return new ResponseEntity<>(
                ExecptionsDetailsValidate.builder()
                        .title("Parâmetros inválidos")
                        .message("Os campos estão inválidos")
                        .fields(errors.stream().map(FieldError::getField)
                                .collect(Collectors.joining(";")))
                        .fieldsMessages(errors.stream().map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(";")))
                        .timestamp(LocalDateTime.now())
                        .build() //new
                ,
                httpStatus);

    }
}
