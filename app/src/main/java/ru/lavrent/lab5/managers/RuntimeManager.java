package ru.lavrent.lab5.managers;

import ru.lavrent.lab5.commands.Add;
import ru.lavrent.lab5.commands.AddIfMax;
import ru.lavrent.lab5.commands.Clear;
import ru.lavrent.lab5.commands.Command;
import ru.lavrent.lab5.commands.CountLessThanDifficulty;
import ru.lavrent.lab5.commands.ExecuteScript;
import ru.lavrent.lab5.commands.Exit;
import ru.lavrent.lab5.commands.Help;
import ru.lavrent.lab5.commands.History;
import ru.lavrent.lab5.commands.Info;
import ru.lavrent.lab5.commands.Load;
import ru.lavrent.lab5.commands.MinByMinimalPoint;
import ru.lavrent.lab5.commands.PrintAscending;
import ru.lavrent.lab5.commands.RemoveById;
import ru.lavrent.lab5.commands.RemoveLower;
import ru.lavrent.lab5.commands.Save;
import ru.lavrent.lab5.commands.Show;
import ru.lavrent.lab5.commands.UpdateById;
import ru.lavrent.lab5.util.Reader;

/**
 * RuntimeManager
 */
public class RuntimeManager {
  CommandManager commandManager;
  Reader reader;

  public RuntimeManager(CollectionManager collectionManager, String filePath) {
    this.commandManager = new CommandManager(collectionManager);
    this.reader = new Reader(commandManager, filePath);
    Command[] commands = new Command[] {
        new Info(collectionManager),
        new CountLessThanDifficulty(collectionManager),
        new Save(collectionManager),
        new Load(collectionManager),
        new Show(collectionManager),
        new Clear(collectionManager),
        new RemoveById(collectionManager),
        new MinByMinimalPoint(collectionManager),
        new PrintAscending(collectionManager),
        new ExecuteScript(collectionManager),
        new Help(commandManager),
        new History(commandManager),
        new Exit(collectionManager, reader),
        new UpdateById(collectionManager, reader),
        new RemoveLower(collectionManager, reader),
        new AddIfMax(collectionManager, reader),
        new Add(collectionManager, reader),
    };
    for (Command cmd : commands) {
      commandManager.addCommand(cmd);
    }
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public Reader getReader() {
    return reader;
  }
}