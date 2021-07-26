package com.ahmedmq.infonote.testcontainers.note;

import java.util.Optional;

public interface NoteRepository {
  Note save(Note note);
  void delete(Long noteId);
  Optional<Note> find(Long noteId);

}
