package com.ahmedmq.infonote.testcontainers.note;


import com.ahmedmq.infonote.testcontainers.user.User;

public interface NoteService {

  Note createNote(CreateNoteRequest noteRequest, User user);

}
