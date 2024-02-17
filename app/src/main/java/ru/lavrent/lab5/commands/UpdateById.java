package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.managers.CommandManager;
import ru.lavrent.lab5.models.LabWork;
import ru.lavrent.lab5.models.forms.LabWorkForm;
import ru.lavrent.lab5.util.Reader;

public class UpdateById extends Command {
  private CollectionManager collectionManager;
  private Reader reader;

  public UpdateById(CollectionManager collectionManager, Reader reader) {
    super("update", "[id] [element] - update by id");
    this.collectionManager = collectionManager;
    this.reader = reader;
  }

  public void execute(String[] args) {
    try {
      final long id = CommandManager.ArgsUtils.getLong(args, 0);
      final LabWork labWork = collectionManager.getById(id);
      if (labWork == null) {
        System.err.println("labwork with id=%d not found".formatted(id));
        return;
      }
      LabWork newLabWork = reader.runForm(new LabWorkForm(collectionManager, labWork));
      collectionManager.updateById(id, newLabWork);
      System.err.println("labwork with id=%d updated successfully".formatted(id));
    } catch (ValidationException e) {
      System.err.println(e.toString());
    } catch (IllegalArgumentException e) {
      System.err.println(e.toString());
    }
  }
}
