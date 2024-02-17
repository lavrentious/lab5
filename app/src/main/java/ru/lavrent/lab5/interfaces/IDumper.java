package ru.lavrent.lab5.interfaces;

import ru.lavrent.lab5.exceptions.DeserializationException;
import ru.lavrent.lab5.exceptions.SerializationException;

import java.io.FileNotFoundException;

public interface IDumper {
  public void dump() throws FileNotFoundException, SerializationException;

  public void load() throws FileNotFoundException, DeserializationException;
}
