package ru.lavrent.lab5.interfaces;

import ru.lavrent.lab5.exceptions.ValidationException;

public interface Validatable {
  void validate() throws ValidationException;
}
