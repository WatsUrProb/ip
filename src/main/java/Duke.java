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




        //creating the scanner
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye BossSe. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }

            System.out.println(line);
            System.out.println("Let Nova help you with this task: -" + input);
            System.out.println(line);

        }
        scanner.close();
    }

}
