import java.io.*;
import java.util.*;

public class FileHandler {
    private static final int MAX_COORD = (int) (Math.sqrt(Integer.MAX_VALUE / 2) / 2);//координаты по модулю должны быть не больше 16383
    private String inFileName;
    private String outFileName;

    private int areaMax;
    private int[] maxTriangle;

    FileHandler(String inFilename, String outFilename) {
        this.inFileName = inFilename;
        this.outFileName = outFilename;
    }

    public String getInFilename() {
        return inFileName;
    }

    public void setInFilename(String inFilename) {
        this.inFileName = inFilename;
    }

    public String getOutFilename() {
        return outFileName;
    }

    public void setOutFilename(String outFilename) {
        this.outFileName = outFilename;
    }

    void process() {
        try {
            FileInputStream fileInputStream = new FileInputStream(inFileName);
            Scanner scanner = new Scanner(fileInputStream);
            outer:
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine(); // получаем исходную строку
                String[] arrStr = str.split(" "); // превращаем её в массив из фрагментов обозначающих координаты
                List<String> arrStrList = new ArrayList<>(Arrays.asList(arrStr)); // превращаем массив в ArrayList, чтобы удалить пустые элементы

                arrStrList.removeIf((s) -> s.equals("")); // удаляем пустые элементы

                if (arrStrList.size() != 6) continue; // отбрасываем массив, если колличество координат некорректно

                int[] triangle = new int[6]; // создаём массив для хранения координат треугольника в формате int

                for (int i = 0; i < 6; i++) { // заполняем массив данными, при обнаружении некорректных символов отбрасываем его
                    String s = arrStrList.get(i).trim();
                    try {
                        triangle[i] = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        continue outer;
                    }
                    if (Math.abs(triangle[i]) > MAX_COORD) continue outer; // отбрасываем массив при выходе координат за допустимые пределы
                }

                if (!checkTriangle(triangle)) continue; // проверяем треугольник на равнобедренность
                int doubleArea = calcDoubleArea(triangle); // вычисляем удвоенную площадь треугольника
                if (doubleArea > areaMax) { // если она превышает найденную ранее, объявляем максимальными её и массив координат
                    areaMax = doubleArea;
                    maxTriangle = triangle;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл с исходными данными");
        }
        writeOut(maxTriangle); // печатаем координаты треугольника с максимальной площадью
    }

    private boolean checkTriangle(int[] triangle) {
        int a = calcLength(triangle[0], triangle[2], triangle[1], triangle[3]);
        int b = calcLength(triangle[0], triangle[4], triangle[1], triangle[5]);
        int c = calcLength(triangle[2], triangle[4], triangle[3], triangle[5]);
        return (a == b || a == c || b == c);
    }

    private int calcLength(int xa, int xb, int ya, int yb) {
        return (int) (Math.pow((xa - xb), 2) + Math.pow((ya - yb), 2));
    }

    private int calcDoubleArea(int[] triangle) {
        return Math.abs((triangle[2] - triangle[0]) * (triangle[5] - triangle[1]) -
                (triangle[4] - triangle[0]) * (triangle[3] - triangle[1]));
    }

    private void writeOut(int[] triangle) {
        try {
            FileWriter fileWriter = new FileWriter(outFileName);
            if (triangle != null) {
                String str = "";
                for (int x : triangle) {
                    str = String.format("%s%s ", str, x);
                }
                fileWriter.write(str);
                fileWriter.flush();
            }
        } catch (IOException e) {
            System.out.println("Не возможно записать файл \"out.txt\"");
        }
    }
}
