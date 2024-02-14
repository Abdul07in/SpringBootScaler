package com.scaler.taskmanger.dto;

import com.scaler.taskmanger.entities.NoteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateNoteResponseDTO {
    private Integer taskId;
    private NoteEntity note;
}
