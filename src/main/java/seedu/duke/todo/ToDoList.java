package seedu.duke.todo;

import seedu.duke.lists.QuotesifyList;

import java.util.ArrayList;

public class ToDoList extends QuotesifyList<ToDo> {
    private ArrayList<ToDo> toDos = super.getList();

    public ToDoList() {
        super(new ArrayList<ToDo>());
    }
    public ToDoList(ArrayList<ToDo> toDos) {
        super(toDos);
    }

    @Override
    public void add(ToDo newToDo) {
        toDos.add(newToDo);
    }

    @Override
    public void delete(int index) {
        toDos.remove(index);
    }

    public ToDo find(int taskNum) {
        if(taskNum <= toDos.size()) {
            return toDos.get(taskNum);
        }
        else {
            return null;
        }
    }

    @Override
    public String toString() {
        String toDosToReturn = "";

        for (ToDo toDo : toDos) {
            toDosToReturn += toDo.toString() + System.lineSeparator();
        }

        return toDosToReturn;
    }
}
