package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CommandManager;

import java.util.List;

public class History extends Command {
  private CommandManager commandManager;

  public History(CommandManager commandManager) {
    super("history", "display the last 11 items of the history");
    this.commandManager = commandManager;
  }

  public void execute(String[] args) {
    List<String> history = commandManager.getLastNHistoryItems(11);
    if (history.size() == 0) {
      System.out.println("history is empty");
      return;
    }
    System.out.println("last %d commands:".formatted(Math.min(11, history.size())));
    int i = 1;
    for (String command : history) {
      System.out.println("%d. %s".formatted(i++, command));
    }
  }
}
