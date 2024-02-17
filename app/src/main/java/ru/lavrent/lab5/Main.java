package ru.lavrent.lab5;

import ru.lavrent.lab5.exceptions.InvalidConfigException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.managers.CommandManager;
import ru.lavrent.lab5.util.EnvConfig;
import ru.lavrent.lab5.util.Reader;

public class Main {
  public static void main(String[] args) {
    CollectionManager collectionManager = new CollectionManager();
    CommandManager commandManager = new CommandManager(collectionManager);
    Reader reader = new Reader(commandManager, null);
    commandManager.setReader(reader);

    try {
      System.out.println(EnvConfig.getInstance().getDbPath());
      reader.read();
    } catch (InvalidConfigException e) {
      System.err.println("config failed validation: " + e.getMessage());
    }
  }
}
