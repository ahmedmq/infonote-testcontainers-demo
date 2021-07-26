package com.ahmedmq.infonote.testcontainers.note;

import com.ahmedmq.infonote.testcontainers.user.User;

public class NoteRule {

  public boolean allowCreateNote(User user){
    return !user.getRole().equals("read");
  }

}
