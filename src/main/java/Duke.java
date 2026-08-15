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
        System.out.println("Or");
        System.out.println("Type -bye- to exit");
        System.out.println("Or");
        System.out.println("Type -list- to see your history of commands");

        System.out.println("____________________________________________________________");
        System.out.println("Type -mark [task_number]- to mark tasks");
        System.out.println("Or");
        System.out.println("Type -unmark [task_number]- to unmark previously marked tasks");




        // Create storage
        String[] tasks = new String[100];
        int taskCount = 0;

        //for mark
        boolean[] isDone = new boolean[100];


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
                    String status;
                    if (tasks[i]==null){
                        break;
                    }
                    if (isDone[i]){
                        status = "[X]";
                    }
                    else{ status = "[ ]";}

                    System.out.println("Task "+ (i+1) + ". "+ status+" "+ tasks[i]);
                    System.out.println("||");
                }
                System.out.println(line);

            }

            //MARK feature
            else if (input.startsWith("mark ")){
                int Task_number = Integer.parseInt(input.substring(5));
                int index = Task_number - 1;
                isDone[index] = true;
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("[X] " + tasks[index]);
                System.out.println(line);
            }

            //UNMARK feature
            else if (input.startsWith("unmark ")){
                int Task_number = Integer.parseInt(input.substring(5));
                int index = Task_number - 1;
                isDone[index] = false;
                System.out.println(line);
                System.out.println("Ok! I've unmarked this task:");
                System.out.println("[ ] " + tasks[index]);
                System.out.println(line);
            }

            //Adding tasks
            else{
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(line);
                System.out.println("Let Nova help you with this task: -" + input);
                System.out.println(line);
            }
        }
        scanner.close();
    }

}
