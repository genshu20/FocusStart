import java.io.*;
import java.util.*;

public class FileHandler {
    private static final int MAX_COORD = (int)(Math.sqrt(Integer.MAX_VALUE/2)/2);//координаты по модулю должны быть не больше 16383
    private String inFileName;
    private String outFileName;

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
        try {
            FileInputStream fileInputStream=new FileInputStream(inFileName);
            Scanner scanner=new Scanner(fileInputStream);
            outer:
            while (scanner.hasNextLine()){
                String str=scanner.nextLine();
                String[] arrStr = str.split(" ");
                List<String> arrStrList = new ArrayList<>(Arrays.asList(arrStr));

                arrStrList.removeIf((s)->s.equals(""));

                if (arrStrList.size() != 6) continue;

                int[] triangle = new int[6];

                for (int i = 0; i < 6; i++) {
                    String s = arrStrList.get(i).trim();
                    try {
                        triangle[i] = Integer.parseInt(s);
                    } catch (NumberFormatException e) {
                        continue outer;
                    }
                    if(Math.abs(triangle[i])>MAX_COORD)continue outer;
                }

                if(!checkTriangle(triangle))continue ;
                int doubleArea=calcDoubleArea(triangle);
                if(doubleArea>=areaMax&&doubleArea!=0){
                    if(!checkForDuplication(triangle)){
                        areaMax=doubleArea;
                        triangleList.add(triangle);
                    }
                    triangleList.removeIf((ints)->calcDoubleArea(ints)<areaMax);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeOut();
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
    private int calcDoubleArea(int[] triangle) {
        return Math.abs((triangle[2] - triangle[0]) * (triangle[5] - triangle[1]) -
                (triangle[4] - triangle[0]) * (triangle[3] - triangle[1]));
    }
    private boolean checkForDuplication(int[]arrInt){
        for(int[]ai:triangleList){
            if(Arrays.equals(arrInt,ai))return true;
        }
        return false;
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

            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
