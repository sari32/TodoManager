import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the persistence and CRUD operations for Task objects.
 */
public class TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final String filePath;
    private int nextId = 1;

    /**
     * Initializes the repository and loads existing data from the file.
     *
     * @param filePath The path to the JSON file storage.
     */
    public TaskRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    /**
     * Loads tasks from the JSON file into memory.
     */
    public void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists())
            return;
        try {
            String content = Files.readString(Paths.get(filePath)).trim();

            if (content.isEmpty() || content.equals("[]")) {
                return;
            }

            // Remove outer brackets
            content = content.substring(1, content.length() - 1);

            // Split by objects
            String[] taskObjects = content.split("},");

            int maxId = 0;
            for (String task : taskObjects) {
                // Fix split artifacts
                task = task.trim();
                if (!task.endsWith("}"))
                    task = task + "}";

                if (task.startsWith(","))
                    task = task.substring(1).trim();

                // Extract fields
                int id = Integer.parseInt(extractValue(task, "id"));
                String title = extractValue(task, "title");
                String desc = extractValue(task, "description");
                String statusStr = extractValue(task, "status");
                TaskStatus status = TaskStatus.valueOf(statusStr);

                Task t = new Task(id, title, desc, status);
                tasks.put(id, t);

                if (id > maxId)
                    maxId = id;
            }
            // Update the auto-increment counter
            this.nextId = maxId + 1;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    /**
     * Helper method to extract a value for a specific key from a JSON string segment.
     */
    private String extractValue(String jsonSegment, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = jsonSegment.indexOf(searchKey);

        if (startIndex == -1) return "";

        startIndex += searchKey.length();

        // Skip whitespace
        while (startIndex < jsonSegment.length() && jsonSegment.charAt(startIndex) == ' ') {
            startIndex++;
        }

        char firstChar = jsonSegment.charAt(startIndex);
        int endIndex;

        if (firstChar == '"') {
            // Case 1: String value - skip opening quote and find the closing quote
            startIndex++;
            endIndex = jsonSegment.indexOf("\"", startIndex);
        } else {
            // Case 2: Primitive or Enum - ends at the next comma or closing brace
            int commaIndex = jsonSegment.indexOf(",", startIndex);
            int braceIndex = jsonSegment.indexOf("}", startIndex);

            // Determine which delimiter comes first to find the end of the value
            if (commaIndex == -1) endIndex = braceIndex;
            else if (braceIndex == -1) endIndex = commaIndex;
            else endIndex = Math.min(commaIndex, braceIndex);
        }
        if (endIndex == -1) return "";

        return jsonSegment.substring(startIndex, endIndex);
    }

    /**
     * Serializes the current task list to JSON format and saves it to the file.
     */
    public void saveToFile() {
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        List<Task> allTasks = listAll();

        for (int i = 0; i < allTasks.size(); i++) {
            Task t = allTasks.get(i);

            json.append(String.format(
                    "  {\"id\":%d, \"title\":\"%s\", \"description\":\"%s\", \"status\":\"%s\"}",
                    t.getId(),
                    escape(t.getTitle()),
                    escape(t.getDescription()),
                    t.getStatus()
            ));

            if (i < allTasks.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("]");

        try {
            Files.writeString(Paths.get(filePath), json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escapes special characters (quotes, newlines) to ensure valid JSON string format.
     */
    private String escape(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"").replace("\n", " ");
    }

    /**
     * Adds a new task with an auto-generated unique ID.
     */
    public Task add(Task task) {
        int id = nextId++;
        Task newTask = new Task(id, task.getTitle(), task.getDescription(), task.getStatus());
        tasks.put(id, newTask);
        saveToFile();
        return newTask;
    }

    /**
     * Updates an existing task.
     *
     * @return true if updated successfully, false if ID not found.
     */
    public boolean update(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            saveToFile();
            return true;
        }
        return false;
    }

    /**
     * Deletes a task by its ID.
     *
     * @return true if deleted successfully, false if ID not found.
     */
    public boolean delete(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            saveToFile();
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