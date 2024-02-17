package ru.lavrent.lab5.models.forms;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.models.Discipline;

import java.util.Scanner;

public class DisciplineForm extends Form<Discipline> {
  public Discipline run(Scanner scanner) throws ValidationException {
    System.out.println("create discipline");
    String name = getString("discipline name: ", scanner, Discipline::validateName);
    long lectureHours = getLong("lecture hours: ", scanner, Discipline::validateLectureHours);
    long practiceHours = getLong("practice hours: ", scanner, Discipline::validatePracticeHours);
    int labsCount = getInt("labs count: ", scanner, Discipline::validateLabsCount);
    return new Discipline(name, lectureHours, practiceHours, labsCount);
  }
}