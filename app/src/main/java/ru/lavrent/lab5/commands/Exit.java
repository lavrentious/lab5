package ru.lavrent.lab5.commands;

import ru.lavrent.lab5.models.forms.Form;
import ru.lavrent.lab5.util.Reader;

import java.util.Objects;

public class Exit extends Command {
  private Reader reader;

  public Exit(Reader reader) {
    super("exit", "quit the application");
    this.reader = Objects.requireNonNull(reader);
  }

  public void execute(String[] args) {
    boolean res = Form.getYN("quit? (y/N) ", reader.getScanner(), false);
    System.out.println(res);
    if (res)
      reader.halt();
  }
}
