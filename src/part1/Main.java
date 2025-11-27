package part1;

import java.util.List;
import java.util.Scanner;

/**
 * The entry point of the application.
 * Provides a text-based user interface (CLI) to interact with the Task Management System.
 */
public class Main {

    // Scanner is static so it can be used across all methods
    private static final Scanner scanner = new Scanner(System.in);
    private static TaskService taskService;

    // --- UI Helper Methods ---

    /**
     * Displays the main menu options to the console.
     */
    private static void printMenu() {
        System.out.println("\nPlease choose an action:");
        System.out.println("1. Add New Task");
        System.out.println("2. List All Tasks");
        System.out.println("3. Mark Task as DONE");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Tasks");
        System.out.println("6. List Tasks Sorted by Status");
        System.out.println("7. Edit Task Details");
        System.out.println("8. Get Task by ID");
        System.out.println("0. Exit");
        System.out.print("> ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Return invalid choice if input is not a number
        }
    }

    // --- Action Handlers ---

    private static void handleAddTask() {
        System.out.println("\n--- Add New Task ---");
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Description: ");
        String description = scanner.nextLine();

        if (title.trim().isEmpty()) {
            System.out.println("Error: Title cannot be empty.");
            return;
        }

        taskService.addTask(title, description);
        System.out.println("Success! Task added.");
    }

    private static void handleListTasks() {
        System.out.println("\n--- All Tasks ---");
        List<Task> tasks = taskService.getAllTasks();
        printTaskList(tasks);
    }

    private static void handleGetTaskById() {
        System.out.println("\n--- Get Task by ID ---");
        System.out.print("Enter Task ID: ");

        int id = getUserChoice();
        Task task = taskService.getTaskById(id);

        if (task == null) {
            System.out.println("Error: No task found with ID " + id);
            return;
        }

        System.out.println("\nTask Details:");
        System.out.printf("%-5s | %-12s | %-20s | %s%n", "ID", "STATUS", "TITLE", "DESCRIPTION");
        System.out.println("---------------------------------------------------------------");

        System.out.printf("%-5d | %-12s | %-20s | %s%n",
                task.getId(),
                task.getStatus(),
                task.getTitle(),
                task.getDescription());
    }

    private static void handleMarkAsDone() {
        System.out.println("\n--- Mark Task as DONE ---");
        System.out.print("Enter Task ID: ");
        int id = getUserChoice();

        boolean success = taskService.markTaskDone(id);
        if (success) {
            System.out.println("Success! Task " + id + " is now DONE.");
        } else {
            System.out.println("Error: Task with ID " + id + " not found.");
        }
    }

    private static void handleDeleteTask() {
        System.out.println("\n--- Delete Task ---");
        System.out.print("Enter Task ID to delete: ");
        int id = getUserChoice();

        boolean success = taskService.deleteTask(id);
        if (success) {
            System.out.println("Success! Task " + id + " deleted.");
        } else {
            System.out.println("Error: Task with ID " + id + " not found.");
        }
    }

    private static void handleSearchTasks() {
        System.out.println("\n--- Search Tasks ---");
        System.out.print("Enter text to search: ");
        String query = scanner.nextLine();

        List<Task> results = taskService.searchTasks(query);
        if (results.isEmpty()) {
            System.out.println("No tasks found matching '" + query + "'.");
        } else {
            System.out.println("Found " + results.size() + " tasks:");
            printTaskList(results);
        }
    }

    private static void handleSortByStatus() {
        System.out.println("\n--- Tasks Sorted by Status ---");
        List<Task> sortedTasks = taskService.getTasksSortedByStatus();
        printTaskList(sortedTasks);
    }

    /**
     * Handles the editing of an existing task (title, description, status).
     * Supports skipping fields by pressing Enter.
     */
    private static void handleEditTask() {
        System.out.print("Enter Task ID to edit: ");
        int id = getUserChoice();

        Task task = taskService.getTaskById(id);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.println("Editing: " + task);
        System.out.println("(Press Enter to keep current value)");

        System.out.print("New Title: ");
        String newTitle = scanner.nextLine();
        if (!newTitle.trim().isEmpty()) {
            task.setTitle(newTitle);
        }

        System.out.print("New Description: ");
        String newDesc = scanner.nextLine();
        if (!newDesc.trim().isEmpty()) {
            task.setDescription(newDesc);
        }

        taskService.updateTask(task);
        System.out.println("Task updated.");
    }

    // Generic method to print any list of tasks nicely
    private static void printTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("(No tasks to display)");
            return;
        }

        // Print table header
        System.out.printf("%-5s | %-12s | %-20s | %s%n", "ID", "STATUS", "TITLE", "DESCRIPTION");
        System.out.println("---------------------------------------------------------------");

        for (Task t : tasks) {
            System.out.printf("%-5d | %-12s | %-20s | %s%n",
                    t.getId(),
                    t.getStatus(),
                    t.getTitle(),
                    t.getDescription());
        }
    }

    public static void main(String[] args) {
        String filePath = "data/tasks.json";

        TaskRepository repository = new TaskRepository(filePath);
        taskService = new TaskService(repository);

        System.out.println("=========================================");
        System.out.println("     Welcome to Java Task Manager   ");
        System.out.println("=========================================");

        //  part1.Main Application Loop
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    handleAddTask();
                    break;
                case 2:
                    handleListTasks();
                    break;
                case 3:
                    handleMarkAsDone();
                    break;
                case 4:
                    handleDeleteTask();
                    break;
                case 5:
                    handleSearchTasks();
                    break;
                case 6:
                    handleSortByStatus();
                    break;
                case 7:
                    handleEditTask();
                    break;
                case 8:
                    handleGetTaskById();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }


}