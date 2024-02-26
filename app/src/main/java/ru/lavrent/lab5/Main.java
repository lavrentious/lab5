package ru.lavrent.lab5;

import ru.lavrent.lab5.exceptions.InvalidConfigException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.managers.RuntimeManager;
import ru.lavrent.lab5.util.EnvConfig;

public class Main {
  public static void main(String[] args) {
    CollectionManager collectionManager = new CollectionManager();
    RuntimeManager runtimeManager = new RuntimeManager(collectionManager, null);

    try {
      System.out.println("using db file: " + EnvConfig.getInstance().getDbPath());
      runtimeManager.getReader().read();
    } catch (InvalidConfigException e) {
      System.err.println("config failed validation: " + e.getMessage());
    }
  }
}
