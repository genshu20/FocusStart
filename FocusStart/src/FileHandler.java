import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileHandler {
    private static final int MAX_COORD = (int)(Math.sqrt(Integer.MAX_VALUE/2)/2);//координаты по модулю должны быть не больше 16383
    private String inFileName;
    private String outFileName;

    private List<String> inputList = new ArrayList<>();
    private List<int[]> triangleList = new ArrayList<>();
    private int areaMax;

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

    void process(){
        readIn();
        for(String str:inputList)addToTriangleList(str);

        triangleList.removeIf((ints)->!checkTriangle(ints));

        areaMax = calcArea(triangleList.get(0));

        for(int[]ints:triangleList){
            int area=calcArea(ints);
            if(area>areaMax)areaMax=area;
        }

        triangleList.removeIf((ints)->calcArea(ints)!=areaMax);
        writeOut();
    }

    private void readIn(){
        try {
            Files.lines(Paths.get(inFileName)).forEach(inputList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addToTriangleList(String str){
        String[] arrStr = str.split(" ");
        List<String> arrStrList = new ArrayList<>(Arrays.asList(arrStr));

        arrStrList.removeIf((s)->s.equals(""));

        if (arrStrList.size() != 6) return;

        int[] triangle = new int[6];

        for (int i = 0; i < 6; i++) {
            String s = arrStrList.get(i).trim();
            try {
                triangle[i] = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return;
            }
            if(Math.abs(triangle[i])>MAX_COORD)return;
        }
        triangleList.add(triangle);
    }
    private boolean checkTriangle(int[]triangle){
        int a = calcLength(triangle[0], triangle[2], triangle[1], triangle[3]);
        int b = calcLength(triangle[0], triangle[4], triangle[1], triangle[5]);
        int c = calcLength(triangle[2], triangle[4], triangle[3], triangle[5]);
        return (a == b || a == c || b == c);
    }
    private int calcLength(int xa, int xb, int ya, int yb) {
        return  (int)(Math.pow((xa - xb), 2) + Math.pow((ya - yb), 2));
    }
    private int calcArea(int[] triangle) {
        return Math.abs((triangle[2] - triangle[0]) * (triangle[5] - triangle[1]) -
                (triangle[4] - triangle[0]) * (triangle[3] - triangle[1]));
    }
    private void writeOut() {
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
}
