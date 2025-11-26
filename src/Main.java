
public class Main {
    public static void main(String[] args) {
        TaskRepository taskRepository = new TaskRepository("sari_tasks");
        taskRepository.add(new Task(0, "Buy groceries", "Milk, eggs, and bread"));
        Task task = taskRepository.getById(3);
        System.out.println(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.update(task);
        taskRepository.delete(1);
        System.out.println(taskRepository.listAll());
    }
}