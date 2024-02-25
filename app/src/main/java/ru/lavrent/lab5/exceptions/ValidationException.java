package ru.lavrent.lab5.exceptions;

/**
 * exception that represents error while checking validity of a field/entity
 */
public class ValidationException extends Exception {
  public ValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationException(String message) {
    super(message);
  }
}
