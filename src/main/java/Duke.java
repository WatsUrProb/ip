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

        // Create storage
        String[] tasks = new String[100];
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
                    if (tasks[i]==null){
                        break;
                    }
                    System.out.println("Task "+ (i+1) + ". : " +tasks[i]);
                    System.out.println("||");
                }
                System.out.println(line);

            }

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
