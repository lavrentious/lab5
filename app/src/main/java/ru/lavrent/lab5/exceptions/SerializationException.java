package ru.lavrent.lab5.exceptions;

public class SerializationException extends Exception {
  public SerializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public SerializationException(String message) {
    super(message);
  }

  public SerializationException(Throwable e) {
    super(e);
  }

  public SerializationException() {
    super();
  }
}
