import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    static List<int[]> triangleList = new ArrayList<>();

    public static void main(String[] args) {
        String inFileName = args[0];
        String outFileName = args[1];
        List<String> inputList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(inFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                inputList.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator iteratorInp = inputList.iterator();
        while (iteratorInp.hasNext()) {
            checkLine((String) iteratorInp.next());
        }
        for (int[] arr : triangleList) {
            System.out.println(Arrays.toString(arr));// проверочка!!!!!!!!!!!!!!!!!!!!
        }
        System.out.println();                       //!!!!!!!!!!!!!!!!!!!!!
        Iterator iteratorTr = triangleList.iterator();
        while (iteratorTr.hasNext()) {
            if (!checkTriangle((int[]) iteratorTr.next())) iteratorTr.remove();
        }
        for (int[] arr : triangleList) {
            System.out.println(Arrays.toString(arr));// проверочка!!!!!!!!!!!!!!!!!!!!
        }
        int areaMax = calcArea(triangleList.get(0));
        System.out.println(areaMax);//!!!!!!!!!!!!!!!!!!!!!!
        Iterator iteratorTr2 = triangleList.iterator();
        while (iteratorTr2.hasNext()) {
            int area = calcArea((int[]) iteratorTr2.next());
            System.out.println(area);                       //!!!!!!!!!!!!!!!!!!!!
            if (area > areaMax) areaMax = area;
        }
        Iterator iteratorTr3 = triangleList.iterator();
        while (iteratorTr3.hasNext()) {
            if (calcArea((int[]) iteratorTr3.next()) != areaMax) iteratorTr3.remove();
        }
        for (int[] arr : triangleList) {
            System.out.println(Arrays.toString(arr));// проверочка!!!!!!!!!!!!!!!!!!!!
        }
        try {
            FileWriter fileWriter = new FileWriter(outFileName);
        for (int[] triangle : triangleList) {
            String str = "";
            for (int x : triangle) {
                str = String.format("%s%s ", str, x);
            }
            fileWriter.write(str + "\n");
            fileWriter.flush();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void checkLine(String str) {
        String[] arrStr = str.split(" ");
        List<String> arrStrList = new ArrayList<>(Arrays.asList(arrStr));
        Iterator<String> iterator = arrStrList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("")) {
                iterator.remove();
            }
        }

        if (arrStrList.size() != 6) return;

        int[] triangle = new int[6];

        for (int i = 0; i < 6; i++) {
            String s = arrStrList.get(i).trim();
            try {
                triangle[i] = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return;
            }
        }
        triangleList.add(triangle);
    }

    static boolean checkTriangle(int[] triangle) {
        int a = calcLength(triangle[0], triangle[2], triangle[1], triangle[3]);
        int b = calcLength(triangle[0], triangle[4], triangle[1], triangle[5]);
        int c = calcLength(triangle[2], triangle[4], triangle[3], triangle[5]);
        return (a == b || a == c || b == c);

    }

    static int calcLength(int xa, int xb, int ya, int yb) {
        int squareLength = (int) (Math.pow((xa - xb), 2) + Math.pow((ya - yb), 2));
        return squareLength;
    }

    static int calcArea(int[] triangle) {
        int doubleArea = Math.abs((triangle[2] - triangle[0]) * (triangle[5] - triangle[1]) -
                (triangle[4] - triangle[0]) * (triangle[3] - triangle[1]));
        return doubleArea;
    }
}
