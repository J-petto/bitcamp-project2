package bitcamp.project2.command;

import bitcamp.project2.vo.PROCESS;
import bitcamp.project2.util.PrintTodoList;
import bitcamp.project2.util.Prompt;
import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;

import java.util.ArrayList;

public class TodayTodoCommand {
    private final TodoList todoList;
    private final ArrayList<Todo> todos;
    private ArrayList<Todo> todayList;

    public TodayTodoCommand(TodoList todoList) {
        this.todoList = todoList;
        System.out.println(todoList);
        todos = todoList.getTodoList();
    }

    private final String[] menus = {"오늘 할 일 보기", "할 일 수정", "할 일 삭제", "할 일 완료"};

    PrintTodoList printTodoList = new PrintTodoList();

    // 오늘 할 일 메뉴 프린트
    private void printTodayTodoMenus() {
        int menuNo = 1;
        System.out.println("[오늘 할 일]");
        for (String menu : menus) {
            System.out.printf("%d. %s\n", menuNo++, menu);
        }
    }

    // 오늘 할 일 시작 부분
    public void executeToday() {
        todayList = todoList.setTodayTodoList();

        printTodayTodoMenus();

        int number;
        String input;

        while (true) {
            input = Prompt.input("메뉴 번호 입력 >");
            if (input.equals("9")) {
                break;
            } else if (input.equalsIgnoreCase("menu")) {
                printTodayTodoMenus();
            } else if (input.equals("1")) {
                todayList = todoList.setTodayTodoList();
                printTodoList.printTodoList(PROCESS.DEFAULT, todayList);
            }
            try {
                number = Integer.parseInt(input);
                if (isAvailable(number)) {
                    System.out.println("올바른 메뉴 번호를 입력해주세요.");
                } else {
                    String menuTitle = menuNo(number);
                    switch (menuTitle) {
                        case "할 일 수정":
                            todayListUpdate();
                            break;
                        case "할 일 삭제":
                            todayListDelete();
                            break;
                        case "할 일 완료":
                            todayComplete();
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("메뉴는 숫자로 입력해주세요.");
            }
        }
    }

    // 오늘 할 일 수정 코드
    private void todayListUpdate() {
        String input;
        int number;
        while (true) {
            input = Prompt.input("수정 할 일 번호 >");
            try {
                number = Integer.parseInt(input);
                if (number < 0) {
                    System.out.println("없는 번호입니다.");
                    break;
                }
                Todo updateTodo = todoList.nullTodo(number, todayList);
                if (updateTodo == null) {
                    System.out.println("없는 할 일입니다.");
                    break;
                }
                updateTodo.setStartDate(Prompt.inputDate("수정할 날짜 입력(ex. 0000-00-00) >"));
                updateTodo.setTitle(Prompt.input("수정할 타이틀 입력 >"));
                isComplete(updateTodo);
                todayList = todoList.setTodayTodoList();
                break;
            } catch (NumberFormatException e) {
                System.out.println("번호로 입력해주세요.");
            }
        }
    }

    // 오늘 할 일 삭제 코드
    private void todayListDelete() {
        String input;
        int number;

        while (true) {
            input = Prompt.input("삭제할 할 일 번호 입력 >");
            try {
                number = Integer.parseInt(input);
                if (number < 0) {
                    System.out.println("없는 번호입니다.");
                    break;
                }

                Todo deleteTodo = todoList.nullTodo(number, todayList);
                if (deleteTodo == null) {
                    System.out.println("없는 할 일입니다.");
                    break;
                }

                for (int i = 0; i < todos.size(); i++) {
                    if (todos.get(i).equals(deleteTodo)) {
                        todos.remove(i);
                    }
                }
                todayList = todoList.setTodayTodoList();
                System.out.println("삭제했습니다");
                break;
            } catch (NumberFormatException e) {
                System.out.println("번호로 입력해주세요.");
            }
        }
    }

    // 오늘 할 일 완료 코드
    private void todayComplete() {
        String input;
        int number;
        while (true) {
            input = Prompt.input("완료한 할 일 번호를 입력해주세요.");
            try {
                number = Integer.parseInt(input);
                if (number < 0) {
                    System.out.println("없는 번호입니다.");
                    break;
                }
                Todo updateTodo = todoList.nullTodo(number, todayList);
                if (updateTodo == null) {
                    System.out.println("없는 할 일입니다.");
                    break;
                }
                isComplete(updateTodo);
                break;
            } catch (NumberFormatException e) {
                System.out.println("번호로 입력해주세요.");
            }
        }
    }

    // 완료 했는지 여부 확인
    private void isComplete(Todo todo) {
        while (true) {
            String complete = Prompt.input("완료 여부를 입력해주세요.");
            if (complete.equalsIgnoreCase("y")) {
                todo.setComplete(true);
                break;
            } else if (complete.equalsIgnoreCase("n")) {
                todo.setComplete(false);
                break;
            } else {
                System.out.println("y 나 n만 입력해주세요.");
            }
        }
    }

    private boolean isAvailable(int number){
        return number < 0 || number > menus.length;
    }

    private String menuNo(int number){
        return menus[number - 1];
    }
}
