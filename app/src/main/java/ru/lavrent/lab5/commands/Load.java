package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.util.EnvConfig;

public class Load extends Command {
  private CollectionManager collectionManager;

  public Load(CollectionManager collectionManager) {
    super("load", "load the collection from the given file");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    try {
      collectionManager.loadFromFile(EnvConfig.getInstance().getDbPath());
      System.out.println("loaded %d records from file %s".formatted(collectionManager.getList().size(),
          EnvConfig.getInstance().getDbPath()));
    } catch (Exception e) {
      System.err.println("could not load: " + e.getMessage());
    }
  }
}
