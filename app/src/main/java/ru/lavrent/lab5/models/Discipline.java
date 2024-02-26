package ru.lavrent.lab5.models;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.interfaces.Validatable;

public class Discipline implements Validatable {
  private String name; // Поле не может быть null, Строка не может быть пустой
  private long lectureHours;
  private Long practiceHours; // Поле может быть null
  private Integer labsCount; // Поле может быть null

  public Discipline(
      String name,
      long lectureHours,
      Long practiceHours,
      Integer labsCount) throws ValidationException {
    this.name = name;
    this.lectureHours = lectureHours;
    this.practiceHours = practiceHours;
    this.labsCount = labsCount;
    validate();
  }

  public String getName() {
    return name;
  }

  public long getLectureHours() {
    return lectureHours;
  }

  public Long getPracticeHours() {
    return practiceHours;
  }

  public Integer getLabsCount() {
    return labsCount;
  }

  public static void validateName(String name) throws ValidationException {
    if (name == null) {
      throw new ValidationException("name must not be null");
    }
    if (name.isEmpty()) {
      throw new ValidationException("name must not be empty");
    }
  }

  public static void validateLectureHours(Long lectureHours) throws ValidationException {
    if (lectureHours == null) {
      throw new ValidationException("lectureHours must not be null");
    }
  }

  public static void validatePracticeHours(Long practiceHours) throws ValidationException {
  }

  public static void validateLabsCount(Integer labsCount) throws ValidationException {
  }

  public void validate() throws ValidationException {
    validateName(name);
    validatePracticeHours(practiceHours);
    validateLectureHours(lectureHours);
    validateLabsCount(labsCount);
  }

  @Override
  public String toString() {
    return "Discipline(name=%s, lectureHours=%d, practiceHours=%d, labsCount=%d)".formatted(name, lectureHours,
        practiceHours, labsCount);
  }
}
