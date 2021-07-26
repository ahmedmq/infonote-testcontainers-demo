# Infonote TestContainers Demo
This repo contains a tutorial on using [TestContainers](https://www.testcontainers.org/) to create a mysql test container for running integration tests.

### About Infonote
Infonote is a simple note taking application. User can create, read, update and delete notes. The intention is to create a series of 'demo' apps that show the gradual evolution of building a fully functional, reliable note taking application. The application is built based on user stories defined in each 'demo' repository

### User Stories

The following is an example set of User Stories for a typical create, read and delete notes from a repository:

- **Story-1**: Should be able to fetch an existing note from the repository
    ```
    Given a note id which exists in the repository
    When I request for the note using the given id
    Then I should get back a full note object from the repository
    ```

- **Story-2**: Should be able to persist a new note into the repository
    ```
    Given a new note object
    When I call a method to persist the given note
    Then the note should be persisted and the note should be returned with the id field set
    ```

- **Story-3**: Should be able to delete an existing note from the repository
    ```
    Given a note id which exists in the repository
    When I call a method to delete the note using the given id
    Then the note should be deleted from the repository
    ```

Given the above user stories as a base, we will build a simple Repository class to perist, fetch and delete notes while using TestContainers to spin up a Mysql database container.

The following are the main classes:

`NoteRepository` - Repository class to store the notes.
