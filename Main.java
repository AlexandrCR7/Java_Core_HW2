import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int dotsForWin = 4;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;

    public static void initialize() {
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];

        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    static void printField() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }
        System.out.println("--------------");
    }

    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.print("""
                    Введите координаты для X и Y\s
                    (от 1 до 3) через пробел
                    """);
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEpty(x, y));
        field[x][y] = DOT_AI;
    }

    static boolean isCellEpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    static boolean checkDrow() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEpty(x, y)) return false;
            }
        }
        return true;
    }

    static boolean checkWin(char dot) {
        for (int i = 0; i < fieldSizeX; i++) {
            int hunamCount = 0;
            int aiCount = 0;
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == DOT_HUMAN) {
                    hunamCount++;
                    aiCount = 0;
                    if (hunamCount == dotsForWin) {
                        return true;
                    }
                } else if (field[i][j] == DOT_AI) {
                    aiCount++;
                    hunamCount = 0;
                    if (aiCount == dotsForWin) {
                        return true;
                    }
                } else {
                    hunamCount = 0;
                    aiCount = 0;
                }
            }
        }
        return false;
    }

    static boolean checkWin1(char dot) {
        int hunamCount = 0;
        int aiCount = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[j][i] == DOT_HUMAN) {
                    hunamCount++;
                    aiCount = 0;
                    if (hunamCount == dotsForWin) {
                        return true;
                    }
                } else if (field[j][i] == DOT_AI) {
                    aiCount++;
                    hunamCount = 0;
                    if (aiCount == dotsForWin) {
                        return true;
                    }
                } else {
                    hunamCount = 0;
                    aiCount = 0;
                }
            }
        }
        return false;
    }


    static boolean checkWin2(char dot) {
        for (int i = 0; i < fieldSizeX; i++) {
            int hunamCount = 0;
            int aiCount = 0;
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == DOT_HUMAN) {
                    hunamCount++;
                    aiCount = 0;
                    if (i < fieldSizeX - 1) {
                        i++;
                    }
                    if (hunamCount == dotsForWin) {
                        return true;
                    }
                } else if (field[i][j] == DOT_AI) {
                    aiCount++;
                    hunamCount = 0;
                    if (i < fieldSizeX - 1) {
                        i++;
                    } else {
                        return false;
                    }
                    if (aiCount == dotsForWin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean checkWin3(char dot) {
        for (int i = fieldSizeX - 1; i > 0; i--) {
            int hunamCount = 0;
            int aiCount = 0;
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == DOT_HUMAN) {
                    hunamCount++;
                    if (i >= 1) {
                        i--;
                        System.out.println(hunamCount);
                    }
                    if (hunamCount == dotsForWin) {
                        return true;
                    }
                } else if (field[i][j] == DOT_AI) {
                    aiCount++;
                    if (i >= 1) {
                        i--;
                    } else {
                        return false;
                    }
                    if (aiCount == dotsForWin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    static boolean checkState(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        } else if (checkWin1(dot)) {
            System.out.println(s);
            return true;
        } else if (checkWin2(dot)) {
            System.out.println(s);
            return true;
        } else if (checkWin3(dot)) {
            System.out.println(s);
            return true;
        } else if (checkDrow()) {
            System.out.println("Ничья!");
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkState(DOT_HUMAN, "Вы победили!")) {
                    break;
                }
                aiTurn();
                printField();
                if (checkState(DOT_AI, "AI победил!")) {
                    break;
                }
            }
            System.out.println("Хотите сыграть еще? Да = Y");
            if (!scanner.next().equalsIgnoreCase("Y")) {
                break;
            }
        }
    }
}

