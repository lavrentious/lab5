package ru.lavrent.lab5.interfaces;

import ru.lavrent.lab5.exceptions.InvalidConfigException;

public interface IConfig {
  public void onLoad();

  public void validate() throws InvalidConfigException;
}
