package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.LabWork;
import ru.lavrent.lab5.models.forms.LabWorkForm;
import ru.lavrent.lab5.util.Reader;

import java.util.Optional;

public class AddIfMax extends Command {
  private CollectionManager collectionManager;
  private Reader reader;

  public AddIfMax(CollectionManager collectionManager, Reader reader) {
    super("add_if_max", "[element] adds the element if it's greater than the greatest existing element");
    this.collectionManager = collectionManager;
    this.reader = reader;
  }

  public void execute(String[] args) {
    if (collectionManager.getCollectionSize() == 0) {
      System.out.println("collection is empty");
      return;
    }
    try {
      LabWork labWork = reader.runForm(new LabWorkForm(collectionManager));
      var collection = collectionManager.getList();
      Optional<LabWork> maxLabWork = collection.stream()
          .max((o1, o2) -> o1.compareTo(o2));
      if (maxLabWork.isPresent() && maxLabWork.get().compareTo(labWork) < 0) {
        collectionManager.add(labWork);
        System.out.println("the specified labwork added successfully");
      } else {
        System.out.println("the specified labwork was not added");
      }
    } catch (ValidationException e) {
      System.err.println("labwork validation failed: " + e.toString());
    }

  }
}
