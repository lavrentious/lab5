package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;

public class Clear extends Command {
  private CollectionManager collectionManager;

  public Clear(CollectionManager collectionManager) {
    super("clear", "clear the collection");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    if (collectionManager.getCollectionSize() == 0) {
      System.out.println("collection is already empty");
      return;
    }
    collectionManager.clear();
    System.out.println("collection cleared");
  }
}
