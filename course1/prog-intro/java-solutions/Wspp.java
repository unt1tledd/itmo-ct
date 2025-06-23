import java.io.File;
import java.io.*;
import java.util.*;
import Sc.MyScanner;
import Sc.IntList;

public class Wspp {

    public static void main(String[] args) {
        Map<String, Integer> wordCount = new LinkedHashMap<>();
        Map<String, IntList> wordPositions = new LinkedHashMap<>();
        int wordPosition = 0;

        try (MyScanner scanner = new MyScanner(new File(args[0]))) {

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (word != null) {
                    word = word.toLowerCase();
                    wordPosition++;
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    wordPositions.putIfAbsent(word, new IntList());
                    wordPositions.get(word).add(String.valueOf(wordPosition));
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при считывании файла: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
            for (String wordKey : wordCount.keySet()) {
                bw.write(wordKey + " " + wordCount.get(wordKey) + " " + wordPositions.get(wordKey).toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}