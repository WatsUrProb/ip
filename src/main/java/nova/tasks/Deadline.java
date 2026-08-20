package nova.tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDateTime by;
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

        return "[D]"+super.toString()+" (by: "+by.format(outputFormat) +")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "X" : "O")
                + " | " + description
                + " | " + by;
    }
}
