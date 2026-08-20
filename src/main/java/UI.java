import java.util.Scanner;

public class UI {

    private final Scanner scanner;
    private final String line =
            "____________________________________________________________";

    public UI() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        String banner = " _   _  _____     ___    \n"
                + "| \\ | |/ _ \\ \\   / / \\   \n"
                + "|  \\| | | | \\ \\ / / _ \\  \n"
                + "| |\\  | |_| |\\ V / ___ \\ \n"
                + "|_| \\_|\\___/  \\_/_/   \\_\\\n";

        System.out.println(line);
        System.out.println(banner);
        System.out.println("Hi there Big Boss! I am your assistant NOVA");
        System.out.println("Your wish is my command!");
        System.out.println(line);
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