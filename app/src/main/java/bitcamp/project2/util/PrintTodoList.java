package bitcamp.project2.util;

import bitcamp.project2.vo.Todo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PrintTodoList {
    private final String noNumberDefaultLine = "==========================================================================";
    private final String useNumberDefaultLine = "===============================================================================";

    // 헤더 생성
    private void printHeader(int process) {
        if(!isDate(process)){
           if(isNo(process)){
               System.out.printf("=====================[%s]=====================\n", LocalDate.now());
           }else {
               System.out.printf("==================[%s]==================\n", LocalDate.now());
           }
        }else {
            if(isNo(process)){
                System.out.println(useNumberDefaultLine);
            }else {
                System.out.println(noNumberDefaultLine);
            }
        }
    }

    //푸터 생성
    private void printFooter(int process){
        if(!isDate(process)){
            if(isNo(process)){
                System.out.println("======================================================");
            }else {
                System.out.println("================================================");
            }
        }else {
            if(isNo(process)){
                System.out.println(useNumberDefaultLine);
            }else {
                System.out.println(noNumberDefaultLine);
            }
        }
    }

    // 넘버 노출 필요?
    private boolean isNo(int process){
        return process % 2 > 0;
    }

    // 데이트 노출 필요? and today 메뉴인가(true = todayO)?
    private boolean isDate(int process){
        return process / 10 == 0;
    }

    public void printTodoList(int process, ArrayList<Todo> todoList) {
        String ansiRed = "\u001B[31m";
        String ansiGreen = "\u001B[32m";
        String ansiGray = "\u001B[90m";
        String ansiEnd = "\u001B[0m";

        printHeader(process);

        for (Todo todo : todoList) {
            System.out.print("|");

            if(isNo(process)){
                System.out.printf("%3d |", todo.getNo());
            }

            if(isDate(process)){
                LocalDate startDate = todo.getStartDate();
                LocalDate endDate = todo.getEndDate();
                boolean complete = todo.isComplete();

                long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
                boolean colorSet = daysBetween <= 3 && !complete && (LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate));

                if(startDate.equals(endDate)){
                    System.out.printf("       %s%s%s        |", colorSet ? ansiRed : "",startDate, ansiEnd);
                }else {
                    System.out.printf(" %s%s ~ %s%s%s%s |", colorSet ? ansiGray : "", startDate, ansiEnd, colorSet ? ansiRed : "", endDate, ansiEnd);
                }
            }

            System.out.printf("%s", todo.isComplete() ? String.format(" %s⬤%s | ", ansiGreen, ansiEnd) : " ⬤ | ");
            printSort(todo.getTitle());
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
