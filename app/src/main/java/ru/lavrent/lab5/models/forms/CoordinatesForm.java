package ru.lavrent.lab5.models.forms;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.models.Coordinates;

import java.util.Scanner;

public class CoordinatesForm extends Form<Coordinates> {
  public Coordinates run(Scanner scanner) throws ValidationException {
    System.out.println("create coordinates");
    Long x = getLong("x: ", scanner, Coordinates::validateX);
    Integer y = getInt("y: ", scanner, Coordinates::validateY);
    return new Coordinates(x, y);
  }
}