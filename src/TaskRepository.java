import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final String filePath;
    private int nextId = 1;

    public TaskRepository(String filePath) {
        this.filePath = filePath;
    }

    public void save() {

    }

    public Task add(Task task) {
        int id = nextId++;
        Task newTask = new Task(id, task.getTitle(), task.getDescription(), task.getStatus());
        tasks.put(id, newTask);
        save();
        return newTask;
    }

    public boolean update(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            save();
            return true;
        }
        return false;
    }

    public boolean delete(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            save();
            return true;
        }
        return false;
    }

    public Task getById(int taskId) {
        return tasks.get(taskId);
    }

    public List<Task> listAll() {
        return new ArrayList<>(tasks.values());
    }
}
