package com.ahmedmq.infonote.testcontainers.note;


import com.ahmedmq.infonote.testcontainers.user.User;

import java.time.LocalDateTime;

public class NoteServiceImpl implements NoteService {

  private final NoteRule noteRule;

  private final NoteRepositoryImpl noteRepository;

  public NoteServiceImpl(NoteRule noteRule, NoteRepositoryImpl noteRepository) {
    this.noteRule = noteRule;
    this.noteRepository = noteRepository;
  }

  @Override
  public Note createNote(CreateNoteRequest noteRequest, User user) {

    if (user == null) {
      throw new IllegalArgumentException("User Cannot be null");
    }

    if (!noteRule.allowCreateNote(user)) {
      throw new UnauthorizedException("Permission Denied");
    }

    Note insertNote = new Note(null,noteRequest.getTitle(), noteRequest.getContent(),user.getName(),LocalDateTime.now());
    return noteRepository.save(insertNote);
  }

}
