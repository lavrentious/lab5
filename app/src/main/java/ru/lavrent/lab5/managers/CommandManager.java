package ru.lavrent.lab5.managers;

import ru.lavrent.lab5.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * class responsible for organizing and invoking commands
 */
public class CommandManager {
  private HashMap<String, Command> commands;
  private ArrayList<String> history;

  public CommandManager() {
    commands = new HashMap<>();
    history = new ArrayList<>();
  }

  /**
   * initialize a new CommandManager
   * 
   * @param collectionManager the CollectionManager to use
   */
  public CommandManager(CollectionManager collectionManager) {
    commands = new HashMap<>();
    history = new ArrayList<>();
  }

  public void addCommand(Command command) {
    commands.put(command.getName(), command);
  }

  public HashMap<String, Command> getCmdMap() {
    return commands;
  }

  public List<Command> getCmdList() {
    return new ArrayList<>(commands.values());
  }

  public Command getCommand(String name) {
    return commands.get(name);
  }

  public void execute(String input) {
    String commandName = ArgsUtils.getCommandFromInput(input);
    String[] commandArgs = ArgsUtils.getArgsFromInput(input);

    Command command = getCommand(commandName);
    if (command != null) {
      history.add(commandName);
      command.execute(commandArgs);
    } else {
      System.err.println("unknown command \"%s\" ".formatted(commandName));
    }
  }

  public List<String> getLastNHistoryItems(int n) {
    return history.subList(Math.max(history.size() - n, 0), history.size());
  }

  /**
   * utilites for processing command arguments
   */
  public static class ArgsUtils {
    /**
     * return the command assuming it's the first word
     * 
     * @param input user input
     * @return the invoked commad
     */
    public static String getCommandFromInput(String input) {
      String[] parts = input.split("\\s+");
      return parts[0];
    }

    public static String[] getArgsFromInput(String input) {
      String[] parts = input.split("\\s+");
      String[] cmdArgs = new String[parts.length - 1];
      System.arraycopy(parts, 1, cmdArgs, 0, cmdArgs.length);
      return cmdArgs;
    }

    /**
     * retrieve i-th argument
     * 
     * @param args command's args list
     * @param i    the number (from 0)
     * @return the i-th arg
     * @throws IllegalArgumentException i-th arument is not found
     */
    public static String getIth(String[] args, int i) {
      if (i < 0 || i >= args.length) {
        throw new IllegalArgumentException("invalid index (must be 0 ≤ i ≤ %s)".formatted(args.length - 1));
      }
      return args[i];
    }

    public static int getInt(String[] args, int i) {
      return Integer.parseInt(getIth(args, i));
    }

    public static long getLong(String[] args, int i) {
      return Long.parseLong(getIth(args, i));
    }
  }
}
