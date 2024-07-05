/*
 * This source file was generated by the Gradle 'init' task
 */
package bitcamp.project2;

import bitcamp.project2.util.Prompt;
import bitcamp.project2.command.TodayTodoCommand;
import bitcamp.project2.command.TodoCommand;
import bitcamp.project2.vo.TodoList;


public class App {
    TodoList todoList = new TodoList();
    TodoCommand todoCommand = new TodoCommand(todoList);
    TodayTodoCommand todayTodoCommand = new TodayTodoCommand(todoList);

    String[] menus = {"오늘의 Todo", "Todo 추가", "Todo 완료", "모든 Todo 목록", "Todo 수정", "Todo 삭제", "종료"};

    public void printMenu() {
        System.out.println("==========[TodoList]==========");
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), menus[i]);
        }
        System.out.println("==============================");
    }

    public void run() {
        todoList.testData();
        printMenu();
        while (true) {
            String choice = Prompt.input("메뉴 >");
            if(choice.equals("menu")){
                printMenu();
                continue;
            }
            try {
                int menuNo = Integer.parseInt(choice);
                if (isAvailable(menuNo)) {
                    System.out.println("없는 메뉴입니다. 재입력해주세요.");
                    continue;
                }
                String menuTitle = menus[menuNo - 1];
                switch (menuTitle) {
                    case "Todo 추가":
                        todoCommand.toDo();
                        break;
                    case "모든 Todo 목록":
                        todoCommand.viewTasks(); // 조회에서는 번호 없이 출력
                        break;
                    case "오늘의 Todo":
                        todayTodoCommand.executeToday();
                        break;
                    case "Todo 수정":
                        todoCommand.updateTask();
                        break;
                    case "Todo 삭제":
                        todoCommand.deleteTask();
                        break;
                    case "종료":
                        System.out.println("종료");
                        return;
                    case "완료":
                        todoCommand.completeTask();
                    default:
                        System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력해주세요.");
            }

        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private boolean isAvailable(int number) {
        return number < 1 || number > menus.length;
    }
}
