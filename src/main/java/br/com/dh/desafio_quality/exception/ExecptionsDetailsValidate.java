package br.com.dh.desafio_quality.exception;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Execptions details validate.
 */
@Builder
@Data
public class ExecptionsDetailsValidate {
    private String title;
    private String message;
    private String fields;
    private String fieldsMessages;
    private LocalDateTime timestamp;
}
