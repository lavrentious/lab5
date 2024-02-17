package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.forms.LabWorkForm;
import ru.lavrent.lab5.util.Reader;

public class Add extends Command {
  private CollectionManager collectionManager;
  private Reader reader;

  public Add(CollectionManager collectionManager, Reader reader) {
    super("add", "add a new element to collection");
    this.collectionManager = collectionManager;
    this.reader = reader;
  }

  public void execute(String[] args) {
    try {
      collectionManager.add(reader.runForm(new LabWorkForm(collectionManager)));
    } catch (ValidationException e) {
      System.err.println("labwork validation failed: " + e.toString());
    }
  }
}
