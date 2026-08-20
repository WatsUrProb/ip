package nova.tasks;

public class Task {
    protected String description;
    String line = "____________________________________________________________";
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void  markDone() {
        isDone = true;
    }
    public void unmarkUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        String done_status;
        if (isDone) {
            done_status = "[X] ";
        }
        else {
            done_status = "[ ] ";
        }
        return done_status + this.description;
    }

    //For converting to text file
    public String toFileString() {
        return "";
    }

}
