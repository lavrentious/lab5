package ru.lavrent.lab5.exceptions;

public class CircularScriptException extends RuntimeException {
  public CircularScriptException(String message, Throwable cause) {
    super(message, cause);
  }

  public CircularScriptException(String message) {
    super(message);
  }

  public CircularScriptException(Throwable e) {
    super(e);
  }

  public CircularScriptException() {
    super();
  }
}
