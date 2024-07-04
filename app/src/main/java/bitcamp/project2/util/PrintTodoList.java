package bitcamp.project2.util;

import bitcamp.project2.vo.PROCESS;
import bitcamp.project2.vo.Todo;

import java.time.LocalDate;
import java.util.ArrayList;

public class PrintTodoList {
    private final String line2 = "======================================================";

    private void createLine(){}

    private void printHeader(int process) {
        switch (process) {
            case PROCESS.TODAY:
                System.out.printf("=====================[%s]=====================\n", LocalDate.now());
                break;
            default:
                System.out.println(line2);
        }
    }

    private void printFooter(int process){
        switch (process) {
            case PROCESS.TODAY:
                System.out.println("==============================================\n");
                break;
            default:
                System.out.println(line2);
        }
    }

    public void printTodoList(int process, ArrayList<Todo> todoList) {
        String ansiRed = "\u001B[31m";
        String ansiEnd = "\u001B[0m";

        printHeader(process);

        if (todoList.isEmpty()) {
            System.out.println("할 일이 없습니다.");
            System.out.println("추가해주세요.");
            return;
        }

        for (Todo todayTodo : todoList) {
            System.out.print("|");
            if(process == PROCESS.DEFAULT){
                System.out.printf("%3d |", todayTodo.getNo());
            }
            System.out.printf("%s", todayTodo.isComplete() ? String.format(" %s⬤%s | ", ansiRed, ansiEnd) : " ⬤ | ");
            printSort(todayTodo.getTitle());
        }

        printFooter(process);
    }

    // 한글 영어 구분
    private void printSort(String title) {
        final int TITLE_MAX = 20;

        int kor = 0;
        int eng = 0;

        System.out.print(title);

        for (char titleChar : title.toCharArray()) {
            if (isKorean(titleChar)) {
                kor++;
                continue;
            }
            eng++;
        }

        int resultSpace = TITLE_MAX - kor - (eng / 2) - (eng % 2);
        for (int i = 0; i <resultSpace ; i++) {
            System.out.print("  ");
        }

        if(resultSpace > 0){
            if(eng % 2 == 1){
                System.out.print(" ");
            }
        }

        System.out.print("|");
        System.out.println();
    }

    private boolean isKorean(char titleChar) {
        return titleChar >= 0xAC00 && titleChar <= 0xD7A3;
    }
}
