package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;

public class Info extends Command {
  private CollectionManager collectionManager;

  public Info(CollectionManager collectionManager) {
    super("info", "info about the collection");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    System.out.println("current collection:");
    System.out.println("type: %s".formatted(collectionManager.getType()));
    System.out.println("creation time: %s %s".formatted(collectionManager.getCreatedAt().toLocalDate(),
        collectionManager.getCreatedAt().toLocalTime()));
    System.out.println("last update time: %s %s".formatted(collectionManager.getUpdatedAt().toLocalDate(),
        collectionManager.getUpdatedAt().toLocalTime()));
  }
}
