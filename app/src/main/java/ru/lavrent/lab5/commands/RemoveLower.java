package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.exceptions.ValidationException;
import ru.lavrent.lab5.managers.CollectionManager;
import ru.lavrent.lab5.models.LabWork;
import ru.lavrent.lab5.models.forms.LabWorkForm;
import ru.lavrent.lab5.util.Reader;

import java.util.List;
import java.util.stream.Collectors;

public class RemoveLower extends Command {
  private CollectionManager collectionManager;
  private Reader reader;

  public RemoveLower(CollectionManager collectionManager, Reader reader) {
    super("remove_lower", "[element] remove all items less than provided item");
    this.collectionManager = collectionManager;
    this.reader = reader;
  }

  public void execute(String[] args) {
    if (collectionManager.getCollectionSize() == 0) {
      System.out.println("collection is empty");
      return;
    }
    try {
      LabWork labWork = reader.runForm(new LabWorkForm(collectionManager));
      var collection = collectionManager.getList();
      List<LabWork> ans = collection.stream().filter(lw -> lw.compareTo(labWork) < 0).collect(Collectors.toList());
      int deletedCount = 0;
      for (LabWork lw : ans) {
        collectionManager.removeById(lw.getId());
        deletedCount++;
      }
      System.out.println("deleted %d elements".formatted(deletedCount));
    } catch (ValidationException e) {
      System.err.println("labwork validation failed: " + e.toString());
    }

  }
}
