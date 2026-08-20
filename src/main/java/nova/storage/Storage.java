package nova.storage;

import nova.tasks.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filepath;
    public Storage(String filepath){
        this.filepath = filepath;
    }
    public void save( ArrayList<Task> tasks ) throws IOException {
        File file = new File(filepath);
        File parentDirectory = file.getParentFile();
        //create a new directory if chatbot first started and first save
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        FileWriter fileWriter = new FileWriter(filepath);
        for(Task task:tasks){
            fileWriter.write(task.toFileString());
            fileWriter.write(System.lineSeparator());
        }
        fileWriter.close();
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filepath);
        if(!file.exists()){
            return tasks;
        }

        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");

            String type = parts[0].trim();
            boolean isDone = parts[1].trim().equals("X");

            //Default new task
            Task task ;

            //Saving_Todo
            if (type.equals("T")){
                String description = parts[2].trim();
                task = new ToDo(description);
            }
            //Saving Deadline
            else if (type.equals("D")){
                String description = parts[2].trim();
                String byString = parts[3].trim();
                LocalDateTime by = LocalDateTime.parse(byString);
                task = new Deadline(description, by);
            }

            else{
                String description = parts[2].trim();
                 String toString = parts[3].trim();
                 String fromString = parts[4].trim();
                 LocalDateTime from = LocalDateTime.parse(fromString);
                 LocalDateTime to = LocalDateTime.parse(toString);
                task = new Event(description, from, to);
            }

            if (isDone){
                task.markDone();
            }
            tasks.add(task);
        }
        scanner.close();
        return tasks;
    }

}

