package nova;

import nova.tasks.*;
import nova.storage.Storage;
import nova.parser.Parser;
import nova.ui.UI;
import nova.exception.NovaException;

import java.io.IOException;


public class Nova {

    private final Storage storage;
    private TaskList tasks;
    private final UI ui;

    public Nova(String filePath) {

        ui = new UI();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Could not load saved nova.tasks.");
            tasks = new TaskList();
        }
    }

    public void run() {

        ui.showWelcome();

        while (true) {

            String input = ui.readCommand();
            String command = Parser.getCommandWord(input);

            try {

                if (command.equals("bye")) {

                    ui.showGoodbye();
                    break;

                } else if (command.equals("list")) {

                    ui.showMessage(
                            "Here are the nova.tasks in your list:\n"
                                    + tasks
                    );

                } else if (command.equals("todo")) {

                    String description = input.substring(4).trim();

                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your todo description is missing.\n"
                                        + "Example: todo read book"
                        );
                    }

                    Task task = new ToDo(description);

                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Got it. I've added this task:\n"
                                    + "  " + task
                                    + "\nNow you have "
                                    + tasks.size()
                                    + " nova.tasks in the list."
                    );

                } else if (command.equals("mark")) {

                    int taskNumber =
                            Parser.parseTaskNumber(input, 4);

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new NovaException(
                                "That task number does not exist."
                        );
                    }

                    tasks.mark(index);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Nice! I've marked this task as done:\n"
                                    + "  " + tasks.get(index)
                    );

                } else if (command.equals("unmark")) {

                    int taskNumber =
                            Parser.parseTaskNumber(input, 6);

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new NovaException(
                                "That task number does not exist."
                        );
                    }

                    tasks.unmark(index);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "OK, I've marked this task as not done yet:\n"
                                    + "  " + tasks.get(index)
                    );

                } else if (command.equals("delete")) {

                    int taskNumber =
                            Parser.parseTaskNumber(input, 6);

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new NovaException(
                                "That task number does not exist."
                        );
                    }

                    Task removedTask = tasks.delete(index);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Noted. I've removed this task:\n"
                                    + "  " + removedTask
                                    + "\nNow you have "
                                    + tasks.size()
                                    + " nova.tasks in the list."
                    );

                } else {
                    throw new NovaException(
                            "NOVA doesn't recognise that command."
                    );
                }

            } catch (NovaException e) {

                ui.showError(e.getMessage());

            } catch (IOException e) {

                ui.showError("NOVA couldn't save your nova.tasks.");
            }
        }

        ui.close();
    }

    public static void main(String[] args) {
        new Nova("./data/nova.txt").run();
    }
}