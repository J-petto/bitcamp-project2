package bitcamp.project2.command;

import bitcamp.project2.vo.PROCESS;
import bitcamp.project2.util.PrintTodoList;
import bitcamp.project2.util.Prompt;
import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;

import java.time.LocalDate;
import java.util.ArrayList;

public class TodayTodoCommand {
    private final TodoList todoList;
    private final ArrayList<Todo> todos;
    private ArrayList<Todo> todayList;

    public TodayTodoCommand(TodoList todoList) {
        this.todoList = todoList;
        todos = todoList.getTodoList();
    }

    private final String[] menus = {"오늘 할 일 보기", "할 일 수정", "할 일 삭제", "할 일 완료"};

    PrintTodoList printer = new PrintTodoList();

    // 오늘 할 일 메뉴 프린트
    private void printTodayTodoMenus() {
        int menuNo = 1;
        System.out.println("[오늘 할 일]");
        for (String menu : menus) {
            System.out.printf("%d. %s\n", menuNo++, menu);
        }
        System.out.println("9. 이전");
    }

    // 오늘 할 일 시작 부분
    public void executeToday() {
        todayList = todoList.setTodayTodoList();

        if(todayList.isEmpty()){
            System.out.println("오늘 할 일이 없습니다.");
            return;
        }

        printer.printTodoList(PROCESS.TODAY_LIST, todayList);

        printTodayTodoMenus();

        int number;
        String input;

        while (true) {
            input = Prompt.input("오늘 할 일 >");

            if (input.equals("9")) {
                break;
            } else if (input.equalsIgnoreCase("menu")) {
                printTodayTodoMenus();
            } else if (input.equals("1")) {
                todayList = todoList.setTodayTodoList();
                if(todayList.isEmpty()){
                    System.out.println("오늘 할 일이 없습니다.");
                    return;
                }
                printer.printTodoList(PROCESS.TODAY_LIST, todayList);
            }
            try {
                number = Integer.parseInt(input);
                if(isAvailable(number)){
                 System.out.println("없는 메뉴입니다. 재입력해주세요.");
                 break;
                }
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
                    default:
                }
            } catch (
                    NumberFormatException e) {
                System.out.println("메뉴는 숫자로 입력해주세요.");
            }
        }
    }

    // 오늘 할 일 수정 코드
    private void todayListUpdate() {
        String input;
        int number;

        printer.printTodoList(PROCESS.TODAY_UPDATE, todayList);

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
                updateTodo.setTitle(Prompt.input("수정할 할 일 내용 입력 >"));
                LocalDate startDate = Prompt.inputDate("수정할 시작일을 입력하세요(yyyymmdd) >");
                updateTodo.setStartDate(startDate);
                while (true){
                    LocalDate endDate = Prompt.inputDate("수정할 종료일을 입력하세요(yyyymmdd) >");
                    if(endDate.isAfter(startDate) || endDate.equals(startDate)){
                        updateTodo.setEndDate(endDate);
                        break;
                    }
                    System.out.println("시작일 보다 이후로 입력해주세요.");
                }
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

        printer.printTodoList(PROCESS.TODAY_DELETE, todayList);

        while (true) {
            input = Prompt.input("삭제할 할 일 번호 입력 >");
            try {
                number = Integer.parseInt(input);

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
                System.out.println("할 일을 삭제했습니다");
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

        printer.printTodoList(PROCESS.TODAY_UPDATE, todayList);

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
            String complete = Prompt.input("완료했습니까?(y/n) >");
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

    // 선택한 메뉴가 유효한 메뉴인지 검증
    private boolean isAvailable(int number) {
        return number <= 0 || number > menus.length;
    }

    // 메뉴 이름 가져오기
    private String menuNo(int number) {
        return menus[number - 1];
    }
}
