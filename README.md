# Java Task Manager

## Description
A simple task management system built in Java.  
Provides basic CRUD functionality for tasks with a console-based user interface.

## Features
- Add new tasks
- List all tasks
- Mark tasks as DONE
- Delete tasks
- Search tasks by text
- List tasks sorted by status
- Edit task details
- View individual task details

## Installation
1. Clone the repository.
2. Open the project in your Java IDE (IntelliJ, Eclipse, etc.).
3. Run the `Main` class to start the program.

## Usage
- Follow the on-screen menu to interact with tasks.
- Task data is stored in `tasks.json` in the project directory.

## Classes
- `Task` – Represents a task entity.
- `TaskRepository` – Manages tasks and handles file storage.
- `TaskService` – Provides operations on tasks.
- `Main` – CLI interface for the user.

## Notes
- Uses only standard Java (no external libraries).
- JSON file is updated after every change.