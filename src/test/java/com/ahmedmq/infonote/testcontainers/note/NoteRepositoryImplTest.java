package com.ahmedmq.infonote.testcontainers.note;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class NoteRepositoryImplTest {

  @Container
  private static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:8.0.26")
    .withDatabaseName("test")
    .withUsername("test")
    .withPassword("s3cret")
    .withInitScript("init_mysql.sql");

  NoteRepositoryImpl noteRepository;

  @BeforeEach
  public void setup() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setURL(mySQLContainer.getJdbcUrl());
    dataSource.setUser(mySQLContainer.getUsername());
    dataSource.setPassword(mySQLContainer.getPassword());
    noteRepository = new NoteRepositoryImpl(dataSource);
  }

  @Test
  @DisplayName("Story-1")
  void shouldReturnNoteForAGivenNoteId() {
    Note fetchedNote = noteRepository.find(99L).get();
    assertNotNull(fetchedNote);
    assertEquals(99L, fetchedNote.getId());
    assertEquals("INIT_TITLE", fetchedNote.getTitle());
    assertEquals("INIT_CONTENT", fetchedNote.getContent());
    assertEquals("ADMIN", fetchedNote.getAuthor());
  }

  @Test
  @DisplayName("Story-2")
  void shouldInsertNoteIntoDatabase() {
    Note insertNote = new Note(null, "Test Title", "Test Content", "Test", LocalDateTime.now());
    Note savedNote = noteRepository.save(insertNote);
    assertNotNull(savedNote);
    assertEquals(100L, savedNote.getId());
  }

  @Test
  @DisplayName("Story-3")
  void shouldDeleteNoteFromRepository() {
    Note insertNote = new Note(null, "Test Title", "Test Content", "Test", LocalDateTime.now());
    Note savedNote = noteRepository.save(insertNote);

    Optional<Note> fetchedNote = noteRepository.find(savedNote.getId());
    assertTrue(fetchedNote.isPresent());

    noteRepository.delete(savedNote.getId());

    fetchedNote = noteRepository.find(savedNote.getId());
    assertFalse(fetchedNote.isPresent());

  }
}
