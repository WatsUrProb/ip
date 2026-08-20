package nova.ui;

import java.util.Scanner;

public class UI {

    private final Scanner scanner;
    private final String line =
            "____________________________________________________________";

    public UI() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
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
        System.out.println("Type -mark [task_number]- to mark nova.tasks");
        System.out.println("Or");
        System.out.println("Type -unmark [task_number]- to unmark previously marked nova.tasks");
        System.out.println(" ");
        System.out.println("____________________________________________________________");
        System.out.println("Type -delete [task_number]- to remove nova.tasks");
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showGoodbye() {
        System.out.println(line);
        System.out.println("Bye Boss. Hope to see you again soon!");
        System.out.println(line);
    }

    public void showMessage(String message) {
        System.out.println(line);
        System.out.println(message);
        System.out.println(line);
    }

    public void showError(String message) {
        System.out.println(line);
        System.out.println("NOVA encountered a problem:");
        System.out.println(message);
        System.out.println(line);
    }

    public void close() {
        scanner.close();
    }
}