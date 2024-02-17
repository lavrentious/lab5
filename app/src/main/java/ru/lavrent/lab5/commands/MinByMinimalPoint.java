package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.LabWork;

public class MinByMinimalPoint extends Command {
  private CollectionManager collectionManager;

  public MinByMinimalPoint(CollectionManager collectionManager) {
    super("min_by_minimal_point", "outputs an element with the lowest minimalPoint");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    var collection = collectionManager.getList();
    if (collectionManager.getCollectionSize() == 0) {
      System.out.println("collection is empty");
      return;
    }
    long minimalPoint = (int) Double.POSITIVE_INFINITY;
    LabWork ans = null;
    for (LabWork labWork : collection) {
      if (labWork.getMinimalPoint() < minimalPoint) {
        ans = labWork;
        minimalPoint = labWork.getMinimalPoint();
      }
    }
    if (ans != null) {
      System.out.println(ans.toString());
    }
  }
}
