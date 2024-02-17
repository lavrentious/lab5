package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.managers.CommandManager;
import ru.lavrent.lab5.models.Difficulty;
import ru.lavrent.lab5.models.LabWork;

public class CountLessThanDifficulty extends Command {
  private CollectionManager collectionManager;

  public CountLessThanDifficulty(CollectionManager collectionManager) {
    super("count_less_than_difficulty", "[difficulty] - print number of elements with diffculty less than given");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    try {
      final int diffculty = CommandManager.ArgsUtils.getInt(args, 0);
      if (diffculty < 0 || diffculty >= Difficulty.values().length) {
        throw new IllegalArgumentException(
            "incorrect difficulty (must be 0 ≤ difficulty ≤ %d)".formatted(Difficulty.values().length - 1));
      }

      var collection = collectionManager.getList();
      int ans = 0;
      for (LabWork labWork : collection) {
        if (labWork.getDifficulty().ordinal() < diffculty) {
          ans++;
        }
      }
      System.out.println("number of labworks in collection with difficulty <%d: %d".formatted(diffculty, ans));
    } catch (IllegalArgumentException e) {
      System.err.println(e.toString());
    }
  }
}
