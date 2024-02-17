package ru.lavrent.lab5.models.forms;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.Coordinates;
import ru.lavrent.lab5.models.Difficulty;
import ru.lavrent.lab5.models.Discipline;
import ru.lavrent.lab5.models.LabWork;

import java.util.Scanner;

public class LabWorkForm extends Form<LabWork> {
  private CollectionManager collectionManager;
  private LabWork oldLabWork;

  public LabWorkForm(CollectionManager collectionManager) {
    this.collectionManager = collectionManager;
  }

  public LabWorkForm(CollectionManager collectionManager, LabWork oldLabWork) {
    this.collectionManager = collectionManager;
    this.oldLabWork = oldLabWork;
  }

  public LabWork run(Scanner scanner) throws ValidationException {
    System.out.println("create labWork");
    String name = getString("labwork name: ", scanner, LabWork::validateName);

    Coordinates coordinates;
    try {
      coordinates = (new CoordinatesForm()).run(scanner);
    } catch (ValidationException e) {
      System.err.println("coordinates' validation failed");
      throw new ValidationException("incorrect coordinates");
    }

    long minimalPoint = getLong("minimal point: ", scanner, LabWork::validateMinimalPoint);
    Difficulty difficulty = getEnumValue("difficulty: ", Difficulty.class, scanner);

    Discipline discipline = (new DisciplineForm()).run(scanner);

    LabWork labWork = new LabWork(oldLabWork == null ? collectionManager.generateId() : oldLabWork.getId(), name,
        coordinates,
        java.time.ZonedDateTime.now(), minimalPoint, difficulty, discipline);
    System.out.println("new labwork created");
    System.out.println(labWork);
    return labWork;
  }
}
