package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.exceptions.InvalidConfigException;
import ru.lavrent.lab5.exceptions.SerializationException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.forms.Form;
import ru.lavrent.lab5.util.EnvConfig;
import ru.lavrent.lab5.util.Reader;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

public class Exit extends Command {
  private Reader reader;
  private CollectionManager collectionManager;

  public Exit(CollectionManager collectionManager, Reader reader) {
    super("exit", "quit the application");
    this.reader = Objects.requireNonNull(reader);
    this.collectionManager = Objects.requireNonNull(collectionManager);
  }

  public void execute(String[] args) {
    boolean hasUnsavedChanges = collectionManager.getHasUnsavedChanges();
    boolean wantsToQuit = Form.getYN("quit? (y/N) ", reader.getScanner(), false);
    if (!wantsToQuit)
      return;
    if (hasUnsavedChanges) {
      boolean saveChanges = Form.getYN("unsaved changes. save before quitting? (Y/n) ", reader.getScanner(), true);
      if (saveChanges) {
        try {
          collectionManager.saveToFile(EnvConfig.getInstance().getDbPath());
        } catch (InvalidConfigException | SerializationException | FileNotFoundException | AccessDeniedException e) {
          System.err.println(e.getMessage());
        }
      }
    }
    reader.halt();
  }
}
