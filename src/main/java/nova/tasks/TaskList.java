package nova.tasks;

import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        assert index >= 0 && index < tasks.size()
                : "Task index should be within task list bounds";
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void mark(int index) {
        tasks.get(index).markDone();
    }

    public void unmark(int index) {
        tasks.get(index).unmarkUndone();
    }

    /**
     * Finds tasks whose descriptions contain the given keywords
     * @param keyword keyword to search for
     * @return task list containing all matching tasks
     */

    public TaskList find(String keyword) {
        TaskList matchingList = new TaskList();
        String lowerCaseKeyword = keyword.toLowerCase();

        tasks.stream()
                .filter(task -> task.getDescription()
                        .toLowerCase()
                        .contains(lowerCaseKeyword))
                .forEach(matchingList::add);

        return matchingList;
    }

    /**
     * Finds an existing event that clashes with the given event.
     *
     * @param newEvent event to check
     * @return clashing event, or null if no clash exists
     */
    public Event findClashingEvent(Event newEvent) {
        for (Task task : tasks) {
            if (!(task instanceof Event)) {
                continue;
            }

            Event existingEvent = (Event) task;

            boolean isClashing =
                    existingEvent.getFrom().isBefore(newEvent.getTo())
                            && newEvent.getFrom().isBefore(existingEvent.getTo());

            if (isClashing) {
                return existingEvent;
            }
        }

        return null;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            result.append(i + 1)
                    .append(".")
                    .append(tasks.get(i))
                    .append(System.lineSeparator());
        }

        return result.toString();
    }
}
