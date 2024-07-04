package bitcamp.project2.vo;

public class PROCESS {
    // 날짜 노출 = process % 10 > 0;
    // 번호 노출 = process % 2 > 0;

    // 모두 노출
    public static final int DEFAULT = 0;

    // MAIN 날짜 노출
    // LIST 번호 미노출
    public static final int MAIN_LIST = 2;

    // UPDATE, DELETE 번호 노출
    public static final int MAIN_UPDATE = 1;
    public static final int MAIN_DELETE = 3;

    // TODAY 날짜 미노출
    // LIST 번호 미노출
    public static final int TODAY_LIST = 12;
    // UPDATE, DELETE 번호 노출
    public static final int TODAY_UPDATE = 11;
    public static final int TODAY_DELETE = 13;
}
