package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.managers.CommandManager;
import ru.lavrent.lab5.util.Reader;

public class ExecuteScript extends Command {
  private CollectionManager collectionManager;

  public ExecuteScript(CollectionManager collectionManager) {
    super("execute_script", "[file] run script from the specified file");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    try {
      final String filePath = CommandManager.ArgsUtils.getIth(args, 0);
      CommandManager commandManager = new CommandManager(collectionManager);
      Reader reader = new Reader(commandManager, filePath);
      commandManager.setReader(reader);

      reader.read();
    } catch (IllegalArgumentException e) {
      System.err.println("argument error: " + e.getMessage());
    }
  }
}
