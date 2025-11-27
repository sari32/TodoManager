# Java Project – Task Manager, Algorithms & API Design

## Overview
This project is divided into three parts:

1. **Task Manager (Java)** – A console-based system for managing tasks with CRUD operations.
2. **Algorithms** – Processing arrays to find strictly increasing subarrays.
3. **API & DTO Design** – Design of REST API and DTOs for library and order/payment systems.

---

## Part 1 – Task Manager

### Description
A simple task management system written in Java.  
Tasks are stored in a JSON file, and users can interact via a console menu.

### Main Classes
- `Task` – Represents a task.
- `TaskRepository` – Handles storage in JSON.
- `TaskService` – Business logic like marking tasks as DONE, searching, and sorting.
- `Main` – CLI for user interaction.

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
3. Run the `Main` class in part1 to start the program.

---

## Part 2 – Algorithms

### Description
Finds all strictly increasing subarrays in an integer array.

### Main Class
- `IncreasingSubarrays` – Implements the algorithm.

### Usage
1. Run the `Main` class in `part2/`.
2. The program prints all strictly increasing subarrays.

---

## Part 3 – API & DTO Design

### Description
Design of REST API endpoints and DTOs for a library system and order/payment system.


### Notes
- DTOs represent the data structure for API communication.
- Demonstrates separation of concerns: entities, storage, business logic, and API design.
