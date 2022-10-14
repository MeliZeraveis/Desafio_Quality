package br.com.dh.desafio_quality.enums;

/**
 * The enum ExceptionType lists the types and descriptions of custom exceptions.
 */
public enum ExceptionType {
  OBJECT_NOT_FOUND("Object not found"),
  PARAMETER_NOT_VALID("Parameter not valid");

  /**
   * The message public attribute.
   */
  public final String message;

  ExceptionType(String message) {
    this.message = message;
  }
}
