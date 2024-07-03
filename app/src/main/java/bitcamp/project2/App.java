/*
 * This source file was generated by the Gradle 'init' task
 */
package bitcamp.project2;

import bitcamp.project2.Prompt.Prompt;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import bitcamp.project2.command.TodayTodoCommand;


public class App {

    Scanner scanner = new Scanner(System.in);
    List<Task> tasks = new ArrayList<>();

    public void run() {
        while (true) {
            printMenu();
            int choice = getIntInput("");

            switch (choice) {
                case 1:
                    toDo();
                    break;
                case 2:
                    viewTasks(false, false); // 조회에서는 번호 없이 출력
                    break;
                case 3:
                    // 기능 넣기
                    break;
                case 4:
                    updateTask();
                    break;
                case 5:
                    deleteTask();
                    break;
                case 0:
                    System.out.println("종료");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    public static void main(String[] args) {
      new TodayTodoCommand().executeToday();

        App app = new App();
        app.run();
    }

    public void printMenu() {
        System.out.println("1. 해야할 일");
        System.out.println("2. 목록 조회");
        System.out.println("3. 조회");
        System.out.println("4. 수정");
        System.out.println("5. 삭제");
        System.out.println("0. 종료");
    }

    public int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("올바른 숫자를 입력해주세요.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    public void toDo() {
        scanner.nextLine(); // Consume the newline
        String list = getStringInput("할 일을 입력하세요: ");
        boolean completed = getBooleanInput("완료했습니까?(y/n): ");
        LocalDate date = getDateInput("날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd): ");
        LocalDate endDate = getDateInput("날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd): ");
        tasks.add(new Task(list, date, completed));
        System.out.println("할 일을 추가했습니다.");
    }

    public void viewTasks(boolean showNumbers, boolean view) {
        if (tasks.isEmpty()) {
            System.out.println("등록된 할 일이 없습니다.");
        } else {
            if (view) {
                System.out.println("no. 완료   할 일       날짜");
            } else {
                System.out.println("완료   할 일       날짜");
            }
            for (int i = 0; i < tasks.size(); i++) {
                if (showNumbers) {
                    System.out.printf("%d. %s\n", i + 1, tasks.get(i));
                } else {
                    System.out.println(tasks.get(i));
                }
            }
        }
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void consumeNewLine() {
        scanner.nextLine();
    }

    public LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String dateString = scanner.nextLine();
            try {
                return Prompt.parseDate(dateString);
            } catch (DateTimeParseException e) {
                System.out.println("올바른 날짜 형식을 입력해주세요 (yyyy-MM-dd 또는 yyyyMMdd).");
            }
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    private boolean getBooleanInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("잘못된 입력입니다. 'y' 또는 'n'을 입력해주세요.");
            }
        }
    }

    public void deleteTask() {
        if (tasks.isEmpty()) {
            System.out.println("삭제할 할 일이 없습니다.");
            return;
        }

        viewTasks(true, true); // 삭제에서는 번호와 함께 출력
        int taskNo = getIntInput("삭제할 할 일 번호를 입력하세요: ");
        boolean found = false;

        for (int i = 0; i < tasks.size(); i++) {
            if (i == taskNo - 1) { // 번호를 인덱스로 변환하여 삭제
                tasks.remove(i);
                System.out.println("할 일이 삭제되었습니다.");
                found = true;
                break; // 요소를 제거한 후 루프를 종료
            }
        }

        if (!found) {
            System.out.println("해당 번호의 할 일을 찾을 수 없습니다.");
        }
    }

    public void updateTask() {
        if (tasks.isEmpty()) {
            System.out.println("수정할 할 일이 없습니다.");
            return;
        }

        viewTasks(true, true); // 수정에서는 번호와 함께 출력
        int taskNo = getIntInput("수정할 할 일 번호를 입력하세요: ") - 1; // 입력된 번호를 인덱스로 변환
        if (taskNo < 0 || taskNo >= tasks.size()) {
            System.out.println("해당 번호의 할 일을 찾을 수 없습니다.");
            return;
        }

        Task task = tasks.get(taskNo);
        scanner.nextLine(); // Consume the newline left-over
        String newTitle = getStringInput("새 할 일을 입력하세요: ");
        LocalDate newDate = getDateInput("새 날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd): ");
        LocalDate endDate = getDateInput("새 날짜를 입력하세요(yyyy-MM-dd 또는 yyyyMMdd): ");
        boolean newCompleted = getBooleanInput("완료했습니까?(y/n): ");

        task.setTitle(newTitle);
        task.setDate(newDate);
        task.setCompleted(newCompleted);

        System.out.println("할 일이 수정되었습니다.");
    }
}
