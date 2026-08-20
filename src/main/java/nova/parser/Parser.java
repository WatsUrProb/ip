package nova.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import nova.exception.NovaException;

public class Parser {

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static String getCommandWord(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty()) {
            return "";
        }

        return trimmedInput.split(" ", 2)[0];
    }

    public static String parseTodoDescription(String input)
            throws NovaException {

        String description = input.substring(4).trim();

        if (description.isEmpty()) {
            throw new NovaException(
                    "Your todo description is missing!\n"
                            + "Example: todo read book"
            );
        }

        return description;
    }

    public static String[] parseDeadlineDetails(String input)
            throws NovaException {

        String details = input.substring(8).trim();

        if (details.isEmpty()) {
            throw new NovaException(
                    "Your deadline description is missing!\n"
                            + "Example: deadline return book /by 2/12/2019 1800"
            );
        }

        if (!details.contains(" /by ")) {
            throw new NovaException(
                    "Your deadline is missing '/by'.\n"
                            + "Example: deadline return book /by 2/12/2019 1800"
            );
        }

        String[] parts = details.split(" /by ", 2);

        String description = parts[0].trim();
        String byString = parts[1].trim();

        if (description.isEmpty()) {
            throw new NovaException(
                    "Your deadline description is missing!"
            );
        }

        if (byString.isEmpty()) {
            throw new NovaException(
                    "Your deadline date and time are missing!"
            );
        }

        return new String[]{description, byString};
    }

    public static String[] parseEventDetails(String input)
            throws NovaException {

        String details = input.substring(5).trim();

        if (details.isEmpty()) {
            throw new NovaException(
                    "Your event description is missing!\n"
                            + "Example: event project meeting "
                            + "/from 19/8/2026 1400 /to 19/8/2026 1600"
            );
        }

        if (!details.contains(" /from ")) {
            throw new NovaException(
                    "Your event is missing '/from'."
            );
        }

        if (!details.contains(" /to ")) {
            throw new NovaException(
                    "Your event is missing '/to'."
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
                    "Your event starting date and time are missing!"
            );
        }

        if (toString.isEmpty()) {
            throw new NovaException(
                    "Your event ending date and time are missing!"
            );
        }

        return new String[]{
                description,
                fromString,
                toString
        };
    }

    public static int parseTaskNumber(String input, int commandLength)
            throws NovaException {

        String numberString =
                input.substring(commandLength).trim();

        if (numberString.isEmpty()) {
            throw new NovaException(
                    "You forgot to provide a task number."
            );
        }

        try {
            return Integer.parseInt(numberString);

        } catch (NumberFormatException e) {
            throw new NovaException(
                    "The task number must be a valid number."
            );
        }
    }

    public static LocalDateTime parseDateTime(String dateTimeString)
            throws NovaException {

        try {
            return LocalDateTime.parse(
                    dateTimeString,
                    DATE_TIME_FORMAT
            );

        } catch (DateTimeParseException e) {
            throw new NovaException(
                    "NOVA couldn't understand that date and time.\n"
                            + "Use this format: 2/12/2019 1800"
            );
        }
    }
}