import java.io.File;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import Sc.MyScanner;
import Sc.IntList;

public class WsppPosition {

    public static void main(String[] args) {
        Map<String, Integer> wordCount = new LinkedHashMap<>();
        Map<String, IntList> wordPositions = new LinkedHashMap<>();
        int lineCount = 0;
        int wordPosition = 0;

        try (MyScanner scanner = new MyScanner(new File(args[0]))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineCount++;
                wordPosition = 0;
                MyScanner scLine = new MyScanner(line);
                while (scLine.hasNext()) {
                    String word = scLine.next();
                    if (word != null) {
                        wordPosition++;
                        word = word.toLowerCase();
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                        wordPositions.putIfAbsent(word, new IntList());
                        wordPositions.get(word).add(String.valueOf(lineCount));
                        wordPositions.get(word).add(String.valueOf(wordPosition));
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при считывании файла: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            for (String wordKey : wordCount.keySet()) {
                bw.write(wordKey + " " + wordCount.get(wordKey) + " " + wordPositions.get(wordKey).toString2ver());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}