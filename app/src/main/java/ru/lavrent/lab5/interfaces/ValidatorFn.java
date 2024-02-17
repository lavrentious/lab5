package ru.lavrent.lab5.interfaces;

import ru.lavrent.lab5.exceptions.ValidationException;

@FunctionalInterface
public interface ValidatorFn<T> {
  void validate(T t) throws ValidationException;
}