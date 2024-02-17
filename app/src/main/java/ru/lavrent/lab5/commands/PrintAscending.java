package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.LabWork;

import java.util.List;
import java.util.stream.Collectors;

public class PrintAscending extends Command {
  private CollectionManager collectionManager;

  public PrintAscending(CollectionManager collectionManager) {
    super("print_ascending", "outputs collection sorted in ascending order");
    this.collectionManager = collectionManager;
  }

  public void execute(String[] args) {
    var collection = collectionManager.getList();
    if (collectionManager.getCollectionSize() == 0) {
      System.out.println("collection is empty");
      return;
    }
    List<LabWork> sorted = collection.stream().sorted((a, b) -> b.compareTo(a))
        .collect(Collectors.toList());
    int i = 1;
    System.out.println("sorted collection:");
    for (LabWork labWork : sorted) {
      System.out.println("%s. %s".formatted(i++, labWork.toString()));
    }
  }
}
