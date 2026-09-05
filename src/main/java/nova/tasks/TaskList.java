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
