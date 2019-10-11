import java.io.*;
// генерирует 13ГБ файл с равнобедренными треугольниками
public class Generator {
    public static void main(String[] args) {
        try {
            FileWriter fileWriter = new FileWriter("in.txt");
        for (int i = -16383; i <=16383 ; i++) {
            for (int j = 0; j <= 16383; j++) {
                int[] arr = {-16383 + j, 0, 16383 - j, 0, 0, i};

                String str = "";
                for (int x : arr) {
                    str = String.format("%s%s ", str, x);
                }
                fileWriter.write(str + "\n");
            }
            fileWriter.flush();
        }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
