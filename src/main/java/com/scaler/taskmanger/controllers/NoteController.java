package com.scaler.taskmanger.controllers;

import com.scaler.taskmanger.dto.CreateNoteDTO;
import com.scaler.taskmanger.dto.CreateNoteResponseDTO;
import com.scaler.taskmanger.entities.NoteEntity;
import com.scaler.taskmanger.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId") Integer taskId) {
        var notes = noteService.getNotesByTaskId(taskId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("")
    public ResponseEntity<CreateNoteResponseDTO> addNote(@PathVariable("taskId") Integer taskId, @RequestBody CreateNoteDTO body) {
        var note = noteService.addNoteForTask(taskId, body.getTitle(), body.getBody());
        return ResponseEntity.ok(new CreateNoteResponseDTO(taskId, note));
    }

    }
