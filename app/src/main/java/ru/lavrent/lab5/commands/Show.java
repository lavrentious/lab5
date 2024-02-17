package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.LabWork;

public class Show extends Command {
  private CollectionManager collectionManager;

  public Show(CollectionManager collectionManager) {
    super("show", "list the elements of the collection");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    var collection = collectionManager.getList();
    if (collectionManager.getCollectionSize() == 0) {
      System.out.println("collection is empty");
      return;
    }
    int i = 1;
    System.out.println("current collection:");
    for (LabWork labWork : collection) {
      System.out.println("%s. %s".formatted(i++, labWork.toString()));
    }
  }
}
