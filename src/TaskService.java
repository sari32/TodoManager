import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer responsible for business logic and data manipulation.
 * Acts as a bridge between the Controller/Main class and the TaskRepository.
 */
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

    /**
     * Marks a specific task as completed (DONE).
     *
     * @param id The ID of the task to update.
     * @return true if the task was found and updated, false otherwise.
     */
    public boolean markTaskDone(int id) {
        Task task = taskRepository.getById(id);
        if (task != null) {
            task.setStatus(TaskStatus.DONE);
            taskRepository.update(task);
            return true;
        }
        return false;
    }

    /**
     * Searches for tasks containing the given text in their title or description.
     *
     * @param text The keyword to search for.
     * @return A list of tasks matching the criteria, or all tasks if the text is empty.
     */
    public List<Task> searchTasks(String text) {
        if (text == null || text.trim().isEmpty()) {
            return taskRepository.listAll();
        }

        String lowerCaseText = text.toLowerCase();

        return taskRepository.listAll().stream()
                .filter(task ->
                        task.getTitle().toLowerCase().contains(lowerCaseText) ||
                                task.getDescription().toLowerCase().contains(lowerCaseText)
                )
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all tasks sorted by their status.
     * Order: NEW -> IN_PROGRESS -> DONE (based on Enum ordinal).
     *
     * @return A sorted list of tasks.
     */
    public List<Task> getTasksSortedByStatus() {
        return taskRepository.listAll().stream()
                .sorted(Comparator.comparing(Task::getStatus))
                .collect(Collectors.toList());
    }

    // --- Basic CRUD Operations ---

    public void addTask(String title, String description) {
        // ID is set to 0 initially, taskRepository will handle the auto-increment
        Task task = new Task(0, title, description);
        taskRepository.add(task);
    }

    /**
     * Overloaded method: Allows creating a task with a specific status instead of the default NEW.
     */
    public void addTask(String title, String description, TaskStatus status) {
        Task task = new Task(0, title, description, status);
        taskRepository.add(task);
    }

    public Task getTaskById(int id) {
        return taskRepository.getById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.listAll();
    }

    public boolean updateTask(Task task) {
        return taskRepository.update(task);
    }

    public boolean deleteTask(int id) {
        return taskRepository.delete(id);
    }


}
