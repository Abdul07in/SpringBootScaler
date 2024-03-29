package com.scaler.taskmanger.controllers;

import com.scaler.taskmanger.dto.CreateTaskDTO;
import com.scaler.taskmanger.dto.ErrorResponseDTO;
import com.scaler.taskmanger.dto.TaskResponseDTO;
import com.scaler.taskmanger.dto.UpdateTaskDTO;
import com.scaler.taskmanger.entities.TaskEntity;
import com.scaler.taskmanger.service.NoteService;
import com.scaler.taskmanger.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final NoteService noteService;
    private final ModelMapper modelMapper = new ModelMapper();

    public TaskController(TaskService taskService, NoteService noteService) {
        this.taskService = taskService;
        this.noteService = noteService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        var tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") Integer id) {
        var task = taskService.getTaskById(id);
        var notes = noteService.getNotesByTaskId(id);
        if (task == null) {
            return ResponseEntity.notFound().build(); // Task found.
        }
        var taskDTO = modelMapper.map(task, TaskResponseDTO.class);
        taskDTO.setNotes(notes);
        return ResponseEntity.ok(taskDTO); // Task not found.
    }

    @PostMapping("/")
    public ResponseEntity<TaskEntity> createTask(@RequestBody CreateTaskDTO body) throws ParseException {
        var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());
        return ResponseEntity.ok(task); // Task created.
    }


    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException {
        var task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());
        if (task == null) {
            return ResponseEntity.notFound().build(); // Task found.
        }
        return ResponseEntity.ok(task); // Task updated.
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(Exception e) {
        if (e instanceof ParseException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date Format"));
        }
//        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error")); // Unknown error.
    }

}
