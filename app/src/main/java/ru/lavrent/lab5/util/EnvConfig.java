package ru.lavrent.lab5.util;

import ru.lavrent.lab5.exceptions.InvalidConfigException;
import ru.lavrent.lab5.interfaces.IConfig;

import java.io.IOException;
import java.nio.file.Paths;

public class EnvConfig implements IConfig {
  private static EnvConfig instance;
  private String dbPath;

  private EnvConfig() throws InvalidConfigException {
    onLoad();
    validate();
  }

  public static EnvConfig getInstance() throws InvalidConfigException {
    if (instance == null) {
      instance = new EnvConfig();
    }
    return instance;
  }

  @Override
  public void onLoad() {
    dbPath = System.getenv("dbPath");
    try {
      dbPath = Paths.get(dbPath).toRealPath().toString();
    } catch (IOException e) {
    }
  }

  @Override
  public void validate() throws InvalidConfigException {
    if (dbPath == null) {
      throw new InvalidConfigException("env var 'dbPath' must be specified");
    }
  }

  public String getDbPath() {
    return dbPath;
  }
}
