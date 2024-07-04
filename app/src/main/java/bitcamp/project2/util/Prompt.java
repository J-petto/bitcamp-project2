package bitcamp.project2.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Prompt {
  static Scanner keyboardScanner = new Scanner(System.in);

  public static String input(String format, Object... args) {
    System.out.printf(format + " ", args);
    return keyboardScanner.nextLine();
  }

  public static int inputInt(String format, Object... args) {
    return Integer.parseInt(input(format, args));
  }

  public static LocalDate inputDate(String format, Object... args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    while (true) {
      try {
        String dateString = input(format, args);
        return LocalDate.parse(dateString, formatter);
      } catch (DateTimeParseException e) {
        System.out.println("yyyy-mm-dd 형식으로 입력해주세요.");
      }
    }
  }

  public static void close() {
    keyboardScanner.close();
  }

  public static LocalDate parseDate(String dateString) {
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
    try {
      if (dateString.contains("-")) {
        return LocalDate.parse(dateString, formatter1);
      } else {
        return LocalDate.parse(dateString, formatter2);
      }
    } catch (DateTimeParseException e) {
      throw new DateTimeParseException("날짜 형식을 입력해주세요 (yyyy-MM-dd 또는 yyyyMMdd).", dateString, 0);
    }
  }
}
