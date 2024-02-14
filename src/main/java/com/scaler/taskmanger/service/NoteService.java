package com.scaler.taskmanger.service;

import com.scaler.taskmanger.entities.NoteEntity;
import com.scaler.taskmanger.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NoteService {

    private TaskService taskService;
    private HashMap<Integer, TaskNotesHolder> taskNotesHolder = new HashMap<>();

    public NoteService(TaskService taskService) {
        this.taskService = taskService;
    }

    class TaskNotesHolder {
        protected int noteId = 1;
        protected ArrayList<NoteEntity> notes = new ArrayList<>();

    }

    public List<NoteEntity> getNotesByTaskId(int taskId) {
        TaskEntity task = taskService.getTaskById(taskId);
        if (task == null) {
            return null;
        }
        if(taskNotesHolder.get(taskId) == null) {
            taskNotesHolder.put(taskId, new TaskNotesHolder());
        }
        return taskNotesHolder.get(taskId).notes;
    }

    public NoteEntity addNoteForTask(int taskId , String title , String body){
        TaskEntity task = taskService.getTaskById(taskId);
        if (task == null) {
            return null;
        }
        if(taskNotesHolder.get(taskId) == null) {
            taskNotesHolder.put(taskId, new TaskNotesHolder());
        }
        TaskNotesHolder taskNotes = taskNotesHolder.get(taskId);
        NoteEntity note = new NoteEntity();
        note.setId(taskNotesHolder.get(taskId).noteId);
        note.setTitle(title);
        note.setBody(body);
        taskNotes.notes.add(note);
        taskNotes.noteId++;
        return note; // Note added.
    }


}
