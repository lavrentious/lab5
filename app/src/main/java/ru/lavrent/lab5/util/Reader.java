package ru.lavrent.lab5.util;

import ru.lavrent.lab5.exceptions.CircularScriptException;
import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.managers.CommandManager;
import ru.lavrent.lab5.models.forms.Form;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

/**
 * class to read input
 */
public class Reader {
  static HashSet<String> visitedFiles = new HashSet<>();
  private Scanner scanner;
  private CommandManager commandManager;
  File file;
  String filePath;
  private boolean fileMode;
  private boolean working;

  /**
   * constructs a Reader object
   * 
   * @param commandManager command manager to execute commands
   * @param fileName       path to file to execute. if null read from console
   * @throws CircularScriptException when reading form file that invokes itself at
   *                                 some point
   */
  public Reader(CommandManager commandManager, String fileName) {
    this.commandManager = commandManager;

    working = true;
    if (fileName == null) {
      fileMode = false;
      scanner = new Scanner(System.in);
    } else {
      fileMode = true;
      try {
        file = Paths.get(fileName).toRealPath().toFile();
        filePath = file.getCanonicalPath();
        scanner = new Scanner(file);
        if (visitedFiles.contains(filePath)) {
          working = false;
          throw new CircularScriptException("file %s has previously been invoked (recursion)".formatted(filePath));
        }
        visitedFiles.add(filePath);
      } catch (IOException e) {
        working = false;
        System.err.println("file %s not found".formatted(fileName));
      }
    }
  }

  /**
   * reads input and executes commands until halted
   */
  public void read() {
    if (fileMode) {
      System.out.println("reading from %s".formatted(filePath));
    }
    while (working) {
      if (!fileMode) {
        System.out.print("\n$ ");
      }
      if (!scanner.hasNextLine())
        break;
      String input = scanner.nextLine();
      if (fileMode) {
        System.out.println("[%s] $ %s".formatted(file.getName(), input));
      }

      try {
        commandManager.execute(input);
      } catch (Exception e) {
        System.err.println("unhandled exception: " + e.getMessage());
        e.printStackTrace(System.err);
      }
    }
    System.out.println("closing scanner %d".formatted(scanner.hashCode()));
    if (filePath != null) {
      visitedFiles.remove(filePath);
    }
    scanner.close();
  }

  /**
   * runs a form
   * 
   * @param form the form to run
   * @param <T>  the type of the result
   * @return result obtained from running the form
   */
  public <T> T runForm(Form<T> form) throws ValidationException {
    return form.run(scanner);
  }

  /**
   * halts the current Reader
   */
  public void halt() {
    working = false;
  }

  /**
   * get the current Scanner
   *
   * @return Scanner instance
   */
  public Scanner getScanner() {
    return scanner;
  }
}