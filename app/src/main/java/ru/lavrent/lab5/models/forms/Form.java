package ru.lavrent.lab5.models.forms;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.interfaces.ValidatorFn;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Form<T> {
  public abstract T run(Scanner scanner) throws ValidationException;

  // TODO: retry while incorrect wrapper function or something
  public static Integer getInt(String message, Scanner scanner, ValidatorFn<Integer> validatorFn) {
    do {
      System.out.print(message);
      try {
        String line = scanner.nextLine();
        Integer ans = line.isEmpty() ? null : Integer.parseInt(line);
        if (validatorFn != null)
          validatorFn.validate(ans);
        return ans;
      } catch (ValidationException e) {
        System.out.println("validation failed: %s. retry.".formatted(e.getMessage()));
      } catch (NumberFormatException e) {
        System.out.println("incorrect number format %s. retry.".formatted(e.getMessage()));
      }
    } while (true);
  }

  public static Long getLong(String message, Scanner scanner, ValidatorFn<Long> validatorFn) {
    do {
      System.out.print(message);
      try {
        String line = scanner.nextLine();
        Long ans = line.isEmpty() ? null : Long.parseLong(line);
        if (validatorFn != null)
          validatorFn.validate(ans);
        return ans;
      } catch (ValidationException e) {
        System.out.println("validation failed: %s. retry.".formatted(e.getMessage()));
      } catch (NumberFormatException e) {
        System.out.println("incorrect number format %s. retry.".formatted(e.getMessage()));
      }
    } while (true);
  }

  public static String getString(String message, Scanner scanner, ValidatorFn<String> validatorFn) {
    do {
      System.out.print(message);
      try {
        String ans = scanner.nextLine();
        if (validatorFn != null)
          validatorFn.validate(ans);
        return ans;
      } catch (ValidationException e) {
        System.out.println("validation failed: %s. retry.".formatted(e.getMessage()));
      }
    } while (true);
  }

  public static boolean getYN(String message, Scanner scanner, Boolean defaultValue) {
    do {
      String choice = getString(message, scanner, null);
      try {
        if (defaultValue != null) {
          if (choice.equalsIgnoreCase("y"))
            return true;
          if (choice.equalsIgnoreCase("n"))
            return false;
          return defaultValue;
        } else if (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
          throw new InputMismatchException("enter y or n. retry.");
        }
        return choice.equalsIgnoreCase("y");
      } catch (InputMismatchException e) {
        System.err.println(e.getMessage());
      }
    } while (true);
  }

  public static <T extends Enum<T>> T getEnumValue(String message, Class<T> enumClass, Scanner scanner) {
    do {
      System.out.println(message);
      for (T option : enumClass.getEnumConstants()) {
        System.out.println(option.ordinal() + ". " + option.name());
      }
      String choice = getString("enter name: ", scanner, null);
      for (T option : enumClass.getEnumConstants()) {
        if (choice.equalsIgnoreCase(option.name())) {
          return option;
        }
      }
      System.err.println("invalid enum key. retry.");
    } while (true);
  }
}
