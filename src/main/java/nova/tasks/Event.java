package nova.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {

        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

        return "[E]" + super.toString() + " (from: " + from.format(outputFormat) + " to: " + to + to.format(outputFormat)+")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "X" : "O")
                + " | " + description
                + " | " + from
                + " | " + to;
    }
}