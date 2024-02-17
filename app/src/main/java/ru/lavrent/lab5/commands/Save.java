package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.util.EnvConfig;

public class Save extends Command {
  private CollectionManager collectionManager;

  public Save(CollectionManager collectionManager) {
    super("save", "save the collection to the given file");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    try {
      collectionManager.saveToFile(EnvConfig.getInstance().getDbPath());
    } catch (Exception e) {
      System.err.println("could not save: " + e.getMessage());
    }
  }
}
