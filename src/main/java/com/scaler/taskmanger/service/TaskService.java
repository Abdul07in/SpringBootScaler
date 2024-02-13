package com.scaler.taskmanger.service;

import com.scaler.taskmanger.entities.TaskEntity;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class TaskService {
    @Getter
    private ArrayList<TaskEntity> tasks = new ArrayList<>();
    private int taskId = 1;
    private SimpleDateFormat deadlineFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public TaskEntity addTask(String title, String description, String deadline) throws ParseException {
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadlineFormatter.parse(deadline));
        task.setCompleted(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    public TaskEntity getTaskById(int id) {
        for (TaskEntity task : tasks) {

            if (task.getId() == id) {
                return task;
            }
        }
        return null; // Task not found.
    }


    public TaskEntity updateTask(int id, String description, String deadline, Boolean completed) throws ParseException {
        TaskEntity task = getTaskById(id);
        if (task == null) {
            return null; // Task not found.
        }
        if (description != null) {
            task.setDescription(description);
        }
        if (deadline != null) {
            task.setDeadline(deadlineFormatter.parse(deadline));
        }
        if (completed != null) {
            task.setCompleted(true);
        }
        return task; // Task updated.
    }

}
