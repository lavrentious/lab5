package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CommandManager;

public class Help extends Command {
  private CommandManager commandManager;

  public Help(CommandManager commandManager) {
    super("help", "display this list");
    this.commandManager = commandManager;
  }

  public void execute(String[] args) {
    System.out.println("available commands:");
    int i = 1;
    for (Command command : commandManager.getCmdList()) {
      System.out.println("%d. %s - %s".formatted(i++, command.getName(), command.getDescription()));
    }
  }
}
