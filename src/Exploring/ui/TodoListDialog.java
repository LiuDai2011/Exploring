package Exploring.ui;

import Exploring.TodoList;
import mindustry.ui.dialogs.BaseDialog;

public class TodoListDialog extends BaseDialog {
    public TodoListDialog() {
        super("todo-list");
        cont.add(TodoList.todo()).row();
        addCloseButton();
    }
}
