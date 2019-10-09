public class Main {

    public static void main(String[] args) {
        String inFileName = args[0];
        String outFileName = args[1];
        FileHandler fileHandler = new FileHandler(inFileName, outFileName);
        fileHandler.process();
    }
}
