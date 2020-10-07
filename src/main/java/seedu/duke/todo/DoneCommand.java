package seedu.duke.todo;

public class DoneCommand {
    private int pageNumber;

    public DoneCommand(int pageNum) {
        this.pageNumber = pageNum;
    }

    public void execute(ToDoList toDos) {
        ToDo targetTask = toDos.find(pageNumber);
        if(targetTask != null) {
            targetTask.setDone(true);
        }
        else{
            System.out.println("Task of given number is not found.");
        }
    }
}
