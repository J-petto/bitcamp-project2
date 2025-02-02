package bitcamp.project2.vo;

import java.time.LocalDate;
import java.util.ArrayList;

public class TodoList {
    private static final ArrayList<Todo> todoList = new ArrayList<>();

    public void testData() {
        todoList.add(new Todo("오늘", LocalDate.parse("2024-07-05"), LocalDate.parse("2024-07-05")));
        todoList.add(new Todo("어제", LocalDate.parse("2024-07-04"), LocalDate.parse("2024-07-04")));
        todoList.add(new Todo("ing", LocalDate.parse("2024-07-01"), LocalDate.parse("2024-07-07")));
        todoList.add(new Todo("내일", LocalDate.parse("2024-07-06"), LocalDate.parse("2024-07-06")));
        todoList.add(new Todo("테스트", LocalDate.parse("2024-07-02"), LocalDate.parse("2024-07-15")));
    }

    // 할 일 리스트 중 오늘 할 일 분류
    public ArrayList<Todo> setTodayTodoList() {
        ArrayList<Todo> todayList = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Todo todo : todoList) {
            if (todo.getStartDate().equals(today) || ingTodo(todo)) {
                todayList.add(todo);
            }
        }
        return todayList;
    }

    // 고른 할 일이 있는지 여부 확인
    public Todo nullTodo(int number, ArrayList<Todo> todoList) {
        for (Todo todo : todoList) {
            if (todo.getNo() == number) {
                return todo;
            }
        }
        return null;
    }

    public ArrayList<Todo> getTodoList() {
        return todoList;
    }

    public boolean ingTodo(Todo todo){
        LocalDate today = LocalDate.now();
        return today.isAfter(todo.getStartDate()) && today.isBefore(todo.getEndDate());
    }

}
