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

            //Deadline task
            else if (input.startsWith("deadline ")) {

                String details = input.substring(9);

                String[] parts = details.split(" /by ", 2);

                String description = parts[0];
                String by = parts[1];

                tasks[taskCount] = new Deadline(description, by);
                taskCount++;

                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);
            }

            //Event task
            else if (input.startsWith("event ")) {
                String details = input.substring(6);
                String[] fromParts = details.split(" /from ", 2);
                String description = fromParts[0];
                String[] toParts = fromParts[1].split(" /to ", 2);
                String from = toParts[0];
                String to = toParts[1];
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);
            }

            //ToDo task
            else if (input.startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new ToDo(description);
                taskCount++;
                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + description);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);
            }

            //Adding tasks
            else{
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(line);
                System.out.println("Let Nova add this task: -" + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }

}
