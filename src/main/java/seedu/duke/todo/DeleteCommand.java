package seedu.duke.todo;

public class DeleteCommand {
    private int taskNumber;

    public DeleteCommand(int taskNum) {
        this.taskNumber = taskNum;
    }

    public void execute(ToDoList toDos) {
        ToDo targetTask = toDos.find(taskNumber);
        if(targetTask != null) {
            toDos.removeTodo(taskNumber);
        }
        else{
            System.out.println("Task of given number is not found.");
        }
    }
}
