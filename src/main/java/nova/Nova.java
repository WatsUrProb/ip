package nova;

import java.io.IOException;
import java.time.LocalDateTime;

import nova.exception.NovaException;
import nova.parser.Parser;
import nova.storage.Storage;
import nova.tasks.Deadline;
import nova.tasks.Event;
import nova.tasks.Task;
import nova.tasks.TaskList;
import nova.tasks.ToDo;
import nova.ui.UI;

/**
 * Main class for the NOVA chatbot.
 * Coordinates user interaction, parsing, task management, and storage.
 */
public class Nova {

    private final Storage storage;
    private TaskList tasks;
    private final UI ui;

    /**
     * Creates a NOVA chatbot and loads saved tasks from the given file.
     *
     * @param filePath path to the task storage file
     */
    public Nova(String filePath) {

        ui = new UI();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Could not load saved tasks.");
            tasks = new TaskList();
        }
    }

    /**
     * Starts the chatbot command loop.
     */
    public void run() {

        ui.showWelcome();

        while (true) {

            String input = ui.readCommand();
            String command = Parser.getCommandWord(input);

            try {

                // ---------------- BYE ----------------

                if (command.equals("bye")) {

                    ui.showGoodbye();
                    break;
                }

                // ---------------- LIST ----------------

                else if (command.equals("list")) {

                    ui.showMessage(
                            "Here are the tasks in your list:\n"
                                    + tasks
                    );
                }

                // ---------------- TODO ----------------

                else if (command.equals("todo")) {

                    String description =
                            Parser.parseTodoDescription(input);

                    Task task = new ToDo(description);

                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Got it. I've added this task:\n"
                                    + "  " + task
                                    + "\nNow you have "
                                    + tasks.size()
                                    + " tasks in the list."
                    );
                }

                // ---------------- DEADLINE ----------------

                else if (command.equals("deadline")) {

                    String[] deadlineParts =
                            Parser.parseDeadlineDetails(input);

                    String description = deadlineParts[0];

                    LocalDateTime by =
                            Parser.parseDateTime(deadlineParts[1]);

                    Task task =
                            new Deadline(description, by);

                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Got it. I've added this deadline:\n"
                                    + "  " + task
                                    + "\nNow you have "
                                    + tasks.size()
                                    + " tasks in the list."
                    );
                }

                // ---------------- EVENT ----------------

                else if (command.equals("event")) {

                    String[] eventParts =
                            Parser.parseEventDetails(input);

                    String description = eventParts[0];

                    LocalDateTime from =
                            Parser.parseDateTime(eventParts[1]);

                    LocalDateTime to =
                            Parser.parseDateTime(eventParts[2]);

                    if (to.isBefore(from)) {
                        throw new NovaException(
                                "Your event cannot end before it starts."
                        );
                    }

                    Task task =
                            new Event(description, from, to);

                    tasks.add(task);
                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Got it. I've added this event:\n"
                                    + "  " + task
                                    + "\nNow you have "
                                    + tasks.size()
                                    + " tasks in the list."
                    );
                }

                // ---------------- MARK ----------------

                else if (command.equals("mark")) {

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
                }

                // ---------------- UNMARK ----------------

                else if (command.equals("unmark")) {

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
                }

                // ---------------- DELETE ----------------

                else if (command.equals("delete")) {

                    int taskNumber =
                            Parser.parseTaskNumber(input, 6);

                    int index = taskNumber - 1;

                    if (index < 0 || index >= tasks.size()) {
                        throw new NovaException(
                                "That task number does not exist."
                        );
                    }

                    Task removedTask =
                            tasks.delete(index);

                    storage.save(tasks.getTasks());

                    ui.showMessage(
                            "Noted. I've removed this task:\n"
                                    + "  " + removedTask
                                    + "\nNow you have "
                                    + tasks.size()
                                    + " tasks in the list."
                    );
                }

                // ---------------- UNKNOWN COMMAND ----------------

                else {

                    throw new NovaException(
                            "NOVA doesn't recognise that command."
                    );
                }

            } catch (NovaException e) {

                ui.showError(e.getMessage());

            } catch (IOException e) {

                ui.showError(
                        "NOVA couldn't save your tasks."
                );
            }
        }

        ui.close();
    }

    /**
     * Starts NOVA using the default task storage file.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new Nova("./data/nova.txt").run();
    }
}