import java.util.Scanner;

public class Duke {
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

        System.out.println("deadline [-description-] /by [-date-]");
        System.out.println("event [-description-] /from [-date-] /to [-date-]");
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




        // Create storage for tasks
        Task[] tasks = new Task[100];
        int taskCount = 0;


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
                for(int i = 0; i < tasks.length; i++){
                    Task Current_Task = tasks[i];
                    String status;
                    if (tasks[i] == null){
                        break;
                    }
                    System.out.println("Task "+ (i+1) + "--"+ Current_Task.toString());
                    System.out.println("||");
                }
                System.out.println(line);

            }

            //MARK feature
            else if (input.startsWith("mark ")){
                int Task_number = Integer.parseInt(input.substring(5));
                int index = Task_number - 1;
                tasks[index].markDone();
            }

            //UNMARK feature
            else if (input.startsWith("unmark ")){
                int Task_number = Integer.parseInt(input.substring(5));
                int index = Task_number - 1;
                tasks[index].unmarkUndone();
            }

            //Event task
            else if (input.startsWith("event")) {
                try {
                    String details = input.substring(5).trim();

                    if (details.isEmpty()) {
                        throw new NovaException(
                                "Your event description is missing!\n"
                                        + "Example: event project meeting /from Mon 2pm /to 4pm"
                        );
                    }

                    if (!details.contains(" /from ")) {
                        throw new NovaException(
                                "Your event is missing '/from' information!\n"
                                        + "Example: event project meeting /from Mon 2pm /to 4pm"
                        );
                    }

                    if (!details.contains(" /to ")) {
                        throw new NovaException(
                                "Your event is missing '/to' information!\n"
                                        + "Example: event project meeting /from Mon 2pm /to 4pm"
                        );
                    }

                    String[] fromParts = details.split(" /from ", 2);

                    String description = fromParts[0].trim();

                    String[] toParts = fromParts[1].split(" /to ", 2);

                    String from = toParts[0].trim();
                    String to = toParts[1].trim();

                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your event description is missing!"
                        );
                    }

                    if (from.isEmpty()) {
                        throw new NovaException(
                                "Your event starting time is missing!"
                        );
                    }

                    if (to.isEmpty()) {
                        throw new NovaException(
                                "Your event ending time is missing!"
                        );
                    }

                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;

                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);

                } catch (NovaException e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }
            }
            //Deadline task
            else if (input.startsWith("deadline")) {
                try {
                    String details = input.substring(8).trim();

                    if (details.isEmpty()) {
                        throw new NovaException(
                                "Your deadline description is missing!\n"
                                        + "Example: deadline return book /by Sunday"
                        );
                    }

                    if (!details.contains(" /by ")) {
                        throw new NovaException(
                                "Your deadline is missing '/by' information!\n"
                                        + "Example: deadline return book /by Sunday"
                        );
                    }

                    String[] parts = details.split(" /by ", 2);

                    String description = parts[0].trim();
                    String by = parts[1].trim();

                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your deadline description is missing!\n"
                                        + "Example: deadline return book /by Sunday"
                        );
                    }

                    if (by.isEmpty()) {
                        throw new NovaException(
                                "Your deadline date/time is missing!\n"
                                        + "Example: deadline return book /by Sunday"
                        );
                    }

                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;

                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount - 1]);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);

                } catch (NovaException e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }
            }

            //ToDo task
            else if (input.startsWith("todo")) {
                try {
                    String description = input.substring(4);
                    if (description.isEmpty()) {
                        throw new NovaException(
                                "Your todo description is missing!\n"
                                        + "Example: todo return book"
                        );
                    }
                    tasks[taskCount] = new ToDo(description);
                    taskCount++;
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + description);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(line);
                } catch (NovaException e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                    System.out.println(line);
                }

            }

            //Adding tasks
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
