package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.managers.CommandManager;

public class RemoveById extends Command {
  private CollectionManager collectionManager;

  public RemoveById(CollectionManager collectionManager) {
    super("remove_by_id", "[id] - delete (if exists) item by its id");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    try {
      final long id = CommandManager.ArgsUtils.getLong(args, 0);
      final boolean ans = collectionManager.removeById(id);
      if (ans) {
        System.out.println("element with id=%s deleted".formatted(id));
      } else {
        System.out.println("element with id=%d was not found".formatted(id));
      }
    } catch (IllegalArgumentException e) {
      System.err.println(e.toString());
    }
  }
}
