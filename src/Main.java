
public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService(new TaskRepository("sari_tasks"));
        taskService.addTask("Workout", "Go for a 30-minute run");
        Task task=taskService.getTaskById(3);
        System.out.println(task);
        task.setTitle("Go to the store");
        task.setDescription("Buy a shirt");
        taskService.updateTask(task);
        taskService.markTaskDone(2);
//        taskService.deleteTask(2);
        System.out.println(taskService.getAllTasks());
        System.out.println(taskService.getTasksSortedByStatus());
        System.out.println(taskService.searchTasks("groceries"));

    }
}