import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Imports for date-time
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Nova {

    //Helper method to catch exception when doing Storage.save()
    private static void saveTasks(Storage storage, ArrayList<Task> tasks) {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            System.out.println("NOVA couldn't save your tasks.");
        }
    }

    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = " _   _  _____     ___    \n"
                + "| \\ | |/ _ \\ \\   / / \\   \n"
                + "|  \\| | | | \\ \\ / / _ \\  \n"
                + "| |\\  | |_| |\\ V / ___ \\ \n"
                + "|_| \\_|\\___/  \\_/_/   \\_\\\n";
        System.out.println("____________________________________________________________");
        System.out.println(banner);
        System.out.println("Hi there Big Boss! I am your assistant NOVA");
        System.out.println("Your wish is my command!");

        System.out.println("____________________________________________________________");
        System.out.println("Type your Command Below");
        System.out.println("____________________________________________________________");

        String s = "dd/hh/yyyy";
        System.out.println("deadline [-description-] /by " + s);
        System.out.println("event [-description-] /from dd/hh/yyyy /to dd/hh/yyyy");
        System.out.println("todo [-description-]");
        System.out.println("____________________________________________________________");



        System.out.println("Or");
        System.out.println("Type -bye- to exit");
        System.out.println("Or");
        System.out.println("Type -list- to see your history of commands");

        System.out.println("____________________________________________________________");
        System.out.println("Type -mark [task_number]- to mark tasks");
        System.out.println("Or");
        System.out.println("Type -unmark [task_number]- to unmark previously marked tasks");
        System.out.println(" ");
        System.out.println("____________________________________________________________");
        System.out.println("Type -delete [task_number]- to remove tasks");
        System.out.println("____________________________________________________________");




        // Create storage for tasks
        Storage storage = new Storage("./data/nova.txt");
        ArrayList<Task> tasks;

        try{ tasks = storage.load();
        }
        catch(IOException e){
            System.out.println("NOVA couldn't load the saved tasks.");
            tasks = new ArrayList<>();
        }


        //creating the scanner
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye Boss. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            if (input.equals("list")){
                System.out.println(line);
                System.out.println("Your Lists:");
                for(int i = 0; i < tasks.size(); i++){
                    Task Current_Task = tasks.get(i);
                    String status;
                    if (Current_Task == null){
                        break;
                    }
                    System.out.println("tasks.Task "+ (i+1) + "--"+ Current_Task.toString());
                    System.out.println("||");
                }
                System.out.println(line);

            }

            //MARK feature
            else if (input.startsWith("mark ")){
                int Task_number = Integer.parseInt(input.substring(5));
                int index = Task_number - 1;
               tasks.get(index).markDone();
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks.get(index));
                System.out.println(line);
                saveTasks(storage, tasks);
            }

            //UNMARK feature
            else if (input.startsWith("unmark ")){
                int Task_number = Integer.parseInt(input.substring(5));
                int index = Task_number - 1;
                tasks.get(index).unmarkUndone();
                System.out.println(line);
                System.out.println("Ok! I've unmarked this task:");
                System.out.println("  " + tasks.get(index));

                System.out.println(line);
                saveTasks(storage, tasks);
            }

            //tasks.Event task
            else if (input.startsWith("event")) {
                try {
                    String details = input.substring(5).trim();

                    if (details.isEmpty()) {
                        throw new NovaException(
                                "Your event description is missing!\n"
                                        + "Example: event project meeting /from 19/8/2026 1400 /to 19/8/2026 1600"
                        );
                    }

                    if (!details.contains(" /from ")) {
                        throw new NovaException(
                                "Your event is missing '/from' information!\n"
                                        + "Example: event project meeting /from 19/8/2026 1400 /to 19/8/2026 1600"
                        );
                    }

                    if (!details.contains(" /to ")) {
                        throw new NovaException(
                                "Your event is missing '/to' information!\n"
                                        + "Example: event project meeting /from 19/8/2026 1400 /to 19/8/2026 1600"
                        );
                    }

                    String[] fromParts = details.split(" /from ", 2);
                    String description = fromParts[0].trim();
                    String[] toParts = fromParts[1].split(" /to ", 2);
                    String fromString = toParts[0].trim();
                    String toString = toParts[1].trim();

                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your event description is missing!"
                        );
                    }
                    if (fromString.isEmpty()) {
                        throw new NovaException(
                                "Your event starting time is missing!"
                        );
                    }
                    if (toString.isEmpty()) {
                        throw new NovaException(
                                "Your event ending time is missing!"
                        );
                    }

                    // Format expected from the user
                    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                    LocalDateTime from = LocalDateTime.parse(fromString, inputFormat);
                    LocalDateTime to = LocalDateTime.parse(toString, inputFormat);

                    tasks.add(new Event(description, from, to));


                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + description);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);

                }
                catch (DateTimeParseException e) {
                    System.out.println(line);
                    System.out.println("Sorry I couldn't understand the event date/time format.");
                    System.out.println("Example: event project meeting " + "/from 19/8/2026 1400 /to 19/8/2026 1600");
                    System.out.println(line);
                }

                catch (NovaException e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }

                saveTasks(storage, tasks);
            }
            //Deadline task
            else if (input.startsWith("deadline")) {
                try {
                    String details = input.substring(8).trim();

                    if (details.isEmpty()) {
                        throw new NovaException(
                                "Your deadline description is missing!\n"
                                        + "Example: deadline return book /by 19/8/2026 1600"
                        );
                    }

                    if (!details.contains(" /by ")) {
                        throw new NovaException(
                                "Your deadline is missing '/by' information!\n"
                                        + "Example: deadline return book /by 19/8/2026 1600"
                        );
                    }

                    String[] parts = details.split(" /by ", 2);

                    String description = parts[0].trim();
                    String byString = parts[1].trim();

                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your deadline description is missing!\n"
                                        + "Example: deadline return book /by 19/8/2026 1600"
                        );
                    }


                    if (byString.isEmpty()) {
                        throw new NovaException(
                                "Your deadline date/time is missing!\n"
                                        + "Example: deadline return book /by 19/8/2026 1600"
                        );
                    }
                    //COnverting date-time String to format
                    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                    LocalDateTime by = LocalDateTime.parse(byString, inputFormat);

                    tasks.add(new Deadline(description, by));

                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + description);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);

                } catch (NovaException e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }

                catch (DateTimeParseException e) {
                    System.out.println(line);
                    System.out.println("Sorry, I couldn't understand that date and time.");
                    System.out.println(
                            "Please use this exact format: 2/12/2019 1800"
                    );
                    System.out.println("E.g prompt: deadline return book /by 2/12/2019 1800");
                    System.out.println(line);
                }
                saveTasks(storage, tasks);
            }

            //tasks.ToDo task
            else if (input.startsWith("todo")) {
                try {
                    String description = input.substring(4);
                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your todo description is missing!\n"
                                        + "Example: todo return book"
                        );
                    }
                    tasks.add(new ToDo(description));
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + description);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } catch (NovaException e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }

            }

            //Deleting tasks
            else if (input.startsWith("delete")) {
                try {
                    String numberString = input.substring(6).trim();
                    // User typed only "delete"
                    if (numberString.isEmpty()) {
                        throw new NovaException(
                                "Boss, you forgot to tell me which task to delete!\n"
                                        + "Example: delete 3"
                        );
                    }
                    int taskNumber = Integer.parseInt(numberString);
                    int index = taskNumber - 1;

                    // Check whether task number exists
                    if (index < 0 || index >= tasks.size()) {
                        throw new NovaException(
                                "Boss, task " + taskNumber + " doesn't exist!\n"
                                        + "Please choose a task from 1 to " + tasks.size()
                        );
                    }

                    Task removedTask = tasks.remove(index);

                    System.out.println(line);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);

                } catch (NumberFormatException e) {
                    System.out.println(line);
                    System.out.println("Boss, the task number must be a number!");
                    System.out.println("Example: delete 3");
                    System.out.println(line);

                } catch (NovaException e) {
                    System.out.println(line);
                    System.out.println("Your Lists:");
                    for(int i = 0; i < tasks.size(); i++){
                        Task Current_Task = tasks.get(i);
                        String status;
                        if (Current_Task == null){
                            break;
                        }
                        System.out.println("tasks.Task "+ (i+1) + "--"+ Current_Task.toString());
                        System.out.println("||");
                    }
                    System.out.println(line);
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }

                saveTasks(storage, tasks);
            }

            //catching subclass error
            else{
                System.out.println(line);
                System.out.println("Sorry Boss, NOVA doesn't recognise that command.");
                System.out.println("Try todo, deadline, event, list, mark, unmark, or bye.");
                System.out.println(line);
            }
        }
        scanner.close();
    }

}
