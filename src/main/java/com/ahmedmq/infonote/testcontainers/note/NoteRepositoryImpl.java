package com.ahmedmq.infonote.testcontainers.note;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.sql.Timestamp.valueOf;

public class NoteRepositoryImpl implements NoteRepository {

  private final MysqlDataSource dataSource;

  public NoteRepositoryImpl(MysqlDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Note save(Note note) {
    System.out.println("Saving Note into repository...");
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(
        "INSERT INTO NOTES(TITLE,CONTENT,AUTHOR,CREATED_AT) VALUES(?,?,?,?);", Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, note.getTitle());
        statement.setString(2, note.getContent());
        statement.setString(3, note.getAuthor());
        statement.setTimestamp(4, valueOf(note.getCreatedAt()));
        statement.executeUpdate();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            note.setId(generatedKeys.getLong(1));
          } else {
            throw new SQLException("Creating note failed, no ID obtained.");
          }
        }

      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return note;
  }

  @Override
  public void delete(Long noteId) {
    System.out.println("Deleting note " + noteId);
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(
        "DELETE FROM NOTES WHERE ID=?;")) {
        statement.setLong(1, noteId);
        statement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Note> find(Long noteId) {
    System.out.println("Fetching note " + noteId);
    try (Connection connection = dataSource.getConnection()) {
      try (PreparedStatement statement = connection.prepareStatement(
        "SELECT ID, TITLE, CONTENT, AUTHOR, CREATED_AT FROM NOTES WHERE ID=?;")) {
        statement.setLong(1, noteId);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            Note note = new Note(
              resultSet.getLong(1),
              resultSet.getString(2),
              resultSet.getString(3),
              resultSet.getString(4),
              resultSet.getTimestamp(5).toLocalDateTime());
            return Optional.of(note);
          } else {
            return Optional.empty();
          }
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
