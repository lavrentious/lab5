package ru.lavrent.lab5.exceptions;

public class InvalidConfigException extends Exception {
  public InvalidConfigException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidConfigException(String message) {
    super(message);
  }
}
