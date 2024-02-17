package ru.lavrent.lab5.exceptions;

public class DeserializationException extends Exception {
  public DeserializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DeserializationException(String message) {
    super(message);
  }

  public DeserializationException(Throwable e) {
    super(e);
  }

  public DeserializationException() {
    super();
  }
}
