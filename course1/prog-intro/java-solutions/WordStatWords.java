import java.io.File;
import java.io.*;
import java.util.*;
import Sc.MyScanner;

public class WordStatWords {

    public static void main(String[] args) {
        Map<String, Integer> wordCount = new TreeMap<>(Comparator.reverseOrder());
        StringBuilder word = new StringBuilder();

        try (MyScanner scanner = new MyScanner(new File(args[0]))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (char el : line.toCharArray()) {
                    if (Character.DASH_PUNCTUATION == Character.getType(el) || Character.isLetter(el) || el == '\'') {
                        word.append(Character.toLowerCase(el));
                    } else {
                        if (!word.toString().isEmpty()) {
                            String currentWord = word.toString();
                            wordCount.put(currentWord, wordCount.getOrDefault(currentWord, 0) + 1);
                            word.setLength(0);
                        }
                    }
                }
                if (word.length() > 0) {
                    String currentWord = word.toString();
                    word.setLength(0);
                    wordCount.put(currentWord, wordCount.getOrDefault(currentWord, 0) + 1);
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при считывании файла: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
            try {
                for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                    bw.write(entry.getKey() + " " + entry.getValue());
                    bw.newLine();
                }
            } finally {
                bw.close();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при считывании файла:" + e.getMessage());
        }
    }
}
