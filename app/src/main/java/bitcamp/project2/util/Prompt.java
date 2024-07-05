package bitcamp.project2.util;

import java.time.DateTimeException;
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    while (true) {
      try {
        String dateString = input(format, args);
        return LocalDate.parse(dateString, formatter);
      } catch (DateTimeParseException e) {
        System.out.println("yyyymmdd 형식으로 입력해주세요.");
      }
    }
  }

  public static void close() {
    keyboardScanner.close();
  }
}
