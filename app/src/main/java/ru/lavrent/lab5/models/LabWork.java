package ru.lavrent.lab5.models;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.interfaces.Validatable;

import java.time.ZonedDateTime;

public class LabWork implements Comparable<LabWork>, Validatable {
  private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                   // поля должно быть уникальным, Значение этого поля должно генерироваться
                   // автоматически
  private String name; // Поле не может быть null, Строка не может быть пустой
  private Coordinates coordinates; // Поле не может быть null
  private java.time.ZonedDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
                                                // автоматически
  private Long minimalPoint; // Поле может быть null, Значение поля должно быть больше 0
  private Difficulty difficulty; // Поле не может быть null
  private Discipline discipline; // Поле не может быть null

  public LabWork(long id, String name, Coordinates coordinates, java.time.ZonedDateTime creationDate, Long minimalPoint,
      Difficulty difficulty, Discipline discipline) throws ValidationException {
    this.id = id;
    this.name = name;
    this.coordinates = coordinates;
    this.creationDate = creationDate;
    this.minimalPoint = minimalPoint;
    this.difficulty = difficulty;
    this.discipline = discipline;
    validate();
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public java.time.ZonedDateTime getCreationDate() {
    return creationDate;
  }

  public long getMinimalPoint() {
    return minimalPoint;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public Discipline getDiscipline() {
    return discipline;
  }

  @Override
  public void validate() throws ValidationException {
    validateId(id);
    validateName(name);
    validateCoordinates(coordinates);
    validateCreationDate(creationDate);
    validateMinimalPoint(minimalPoint);
    validateDifficulty(difficulty);
    validateDiscipline(discipline);
  }

  public static void validateId(Long id) throws ValidationException {
    if (id == null) {
      throw new ValidationException("id must be not null");
    }
    if (id <= 0) {
      throw new ValidationException("id must be greater than 0");
    }
  }

  public static void validateName(String name) throws ValidationException {
    if (name == null) {
      throw new ValidationException("name must not be null");
    }
    if (name.isEmpty()) {
      throw new ValidationException("name must not be empty");
    }
  }

  public static void validateCoordinates(Coordinates coordinates) throws ValidationException {
    if (coordinates == null) {
      throw new ValidationException("coordinates must be not null");
    }
  }

  public static void validateCreationDate(ZonedDateTime creationDate) throws ValidationException {
    if (creationDate == null) {
      throw new ValidationException("creationDate must be not null");
    }
  }

  public static void validateMinimalPoint(Long minimalPoint) throws ValidationException {
    if (minimalPoint == null) {
      throw new ValidationException("minimalPoint must not be null");
    }
    if (minimalPoint <= 0) {
      throw new ValidationException("minimalPoint must be greater than 0");
    }
  }

  public static void validateDifficulty(Difficulty difficulty) throws ValidationException {
    if (difficulty == null) {
      throw new ValidationException("difficulty must be not null");
    }
  }

  public static void validateDiscipline(Discipline discipline) throws ValidationException {
    if (discipline == null) {
      throw new ValidationException("discipline must be not null");
    }
  }

  @Override
  public String toString() {
    return "LabWork(id=%d, name=%s, coordinates=%s, creationDate=%s, minimalPoint=%d, difficulty=%s, discipline=%s)"
        .formatted(id, name, coordinates.toString(), creationDate.toString(), minimalPoint, difficulty.name(),
            discipline);
  }

  @Override
  public int compareTo(LabWork o) {
    return Long.compare(getId(), o.getId()); // FIXME: what do i compare by??
  }
}
