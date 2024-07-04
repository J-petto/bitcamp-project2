package bitcamp.project2.command;

import bitcamp.project2.PROCESS;
import bitcamp.project2.Prompt.PrintTodoList;
import bitcamp.project2.Prompt.Prompt;
import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;

import java.time.LocalDate;
import java.util.ArrayList;

public class TodoCommand {
    private final TodoList todoList;
    private final ArrayList<Todo> todos;
    private final PrintTodoList printer = new PrintTodoList();

    public TodoCommand(TodoList todoList) {
        this.todoList = todoList;
        todos = todoList.getTodoList();
    }

    public void toDo() {
        String title = Prompt.input("할 일을 입력하세요: ");
        LocalDate startDate = Prompt.inputDate("날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd): ");
        LocalDate endDate = Prompt.inputDate("날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd): ");

        todos.add(new Todo(title, startDate, endDate));
        System.out.println("할 일을 추가했습니다.");
    }

    public void viewTasks() {
        if (todos.isEmpty()) {
            System.out.println("등록된 할 일이 없습니다.");
            return;
        }
        printer.printTodoList(PROCESS.DEFAULT, todos);
    }

    public void deleteTask() {
        if (todos.isEmpty()) {
            System.out.println("삭제할 할 일이 없습니다.");
            return;
        }
        printer.printTodoList(PROCESS.TODAY, todos);// 삭제에서는 번호와 함께 출력

        while (true) {
            String number = Prompt.input("삭제할 할 일 번호를 입력하세요: ");
            try {
                int menuNo = Integer.parseInt(number);
                Todo deleteTodo = todoList.nullTodo(menuNo, todos);
                if (deleteTodo == null) {
                    System.out.println("없는 할 일 입니다.");
                    break;
                }
                for (int i = 0; i < todos.size(); i++) {
                    if (todos.get(i) == deleteTodo) {
                        todos.remove(i);
                    }
                }
                System.out.println("할 일이 삭제되었습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("번호로 입력해주세요.");
            }
        }
    }

    public void updateTask() {
        while (true) {
            String update = Prompt.input("수정할 할 일 번호를 입력하세요:");
            try {
                int update2 = Integer.parseInt(update);
                if (todos.isEmpty()) {
                    System.out.println("수정할 할 일이 없습니다.");
                    break;
                }
                printer.printTodoList(PROCESS.DEFAULT, todos);
                Todo updateTodo = todoList.nullTodo(update2, todos);

                if (updateTodo == null) {
                    System.out.println("할 일이 없습니다.");
                    break;
                }

                updateTodo.setTitle(Prompt.input("새 할 일을 입력하세요: "));
                updateTodo.setStartDate(Prompt.inputDate("새 날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd):"));
                updateTodo.setEndDate(Prompt.inputDate("새 날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd):"));

                while (true) {
                    String newCompleted = Prompt.input("완료했습니까?(y/n): ");

                    if (newCompleted.equals("y")) {
                        updateTodo.setComplete(true);
                        break;
                    } else if (newCompleted.equals("n")) {
                        updateTodo.setComplete(false);
                        break;
                    } else {
                        System.out.println("y나 n을 입력해주세요.");
                    }
                }
                System.out.println("할 일이 수정되었습니다.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력해주세요.");
            }
        }
    }

//    public TodoList getTodoList() {
//        return todoList;
//    }
}

