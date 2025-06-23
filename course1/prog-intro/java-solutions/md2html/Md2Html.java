package md2html;

import java.io.*;

import java.util.HashMap;
import java.util.Map;
import Sc.*;

import static java.lang.Character.isWhitespace;

public class Md2Html {

    public static Integer checkTag(String line, String tag) {
        int i = 0;
        int length = line.length();
        while (i < length) {
            if (line.charAt(i) == tag.charAt(0) && i > 0 && line.charAt(i - 1) != '\\') {
                if (tag.length() > 1) {
                    if (line.charAt(i+1) == tag.charAt(1)) {
                        return i + 1;
                    }
                } else {
                    return i;
                }
            }
            i++;
        }
        return null;
    }

    public static String addTags(String line) {
        StringBuilder result = new StringBuilder();
        Map<String, String> tags = Map.of(
                "*", "em",
                "**", "strong",
                "_", "em",
                "__", "strong",
                "--", "s",
                "`", "code",
                "~", "mark"
        );

        Map<String, Boolean> openTags = new HashMap<>(Map.ofEntries(
                Map.entry("em", false),
                Map.entry("s", false),
                Map.entry("strong", false),
                Map.entry("code", false),
                Map.entry("mark", false)
        ));

        int length = line.length();
        int i = 0;

        while (i < length) {
            StringBuilder c = new StringBuilder(Character.toString(line.charAt(i)));

            if (i + 1 < length && tags.containsKey((line.substring(i, i + 2)))) {
                c.append(line.charAt(i + 1));
                i++;
            }

            String tagType = tags.get(c.toString());

            boolean spaceRight = (i > 0 && isWhitespace(line.charAt(i - c.length())));
            boolean spaceLeft = (i + 1 < length && isWhitespace(line.charAt(i + 1)));
            boolean isEscaped = i > 0 && line.charAt(i - 1) == '\\';
            boolean flag = true;

            if (c.toString().equals("~") && !isEscaped) {
                Integer ind = checkTag(line.substring(i + 1), c.toString());
                if (ind != null) {
                    ind += i + 1;
                    String substr = addTags(line.substring(i + 1, ind));
                    result.append("<mark>").append(substr).append("</mark>");
                    i = ind;
                } else {
                    result.append("~");
                }
                flag = false;
            }


            if ((c.toString().equals("*") || c.toString().equals("_"))) {
                if (spaceLeft && spaceRight) {
                    tagType = null;
                }
            }

            if (flag) {
                if (tagType != null && !isEscaped) {
                    if (openTags.get(tagType)) {
                        result.append("</").append(tagType).append(">");
                    } else {
                        result.append("<").append(tagType).append(">");
                    }
                    openTags.put(tagType, !openTags.get(tagType));
                } else {
                    switch (c.toString()) {
                        case "<" -> result.append("&lt;");
                        case ">" -> result.append("&gt;");
                        case "&" -> result.append("&amp;");
                        case "\\" -> {
                        }
                        default -> result.append(c);
                    }
                }
            }
            i++;
        }

        for (Map.Entry<String, Boolean> entry : openTags.entrySet()) {
            if (entry.getValue()) {
                result.append("</").append(entry.getKey()).append(">");
            }
        }

        return result.toString();
    }

    public static String convertToHeading(String line) {
        int level = 0;

        while (level < line.length() && line.charAt(level) == '#') {
            level++;
        }

        if (level > 0 && line.length() > level && line.charAt(level) == ' ') {
            String content = line.substring(level + 1).trim();
            return "<h" + level + ">" + addTags(content) + "</h" + level + ">";
        }

        return null;
    }

    public static String convertToParagraph(String line) {
        return "<p>" + addTags(line) + "</p>";
    }

    public static void main(String[] args) {
        StringBuilder out = new StringBuilder();
        try (MyScanner scanner = new MyScanner(new File(args[0]))) {
            StringBuilder new_line = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    if (new_line.length() > 0) {
                        new_line.deleteCharAt(new_line.length() - 1);
                        String heading = convertToHeading(new_line.toString());
                        if (heading != null) {
                            out.append(heading);
                        } else {
                            String paragraph = convertToParagraph(new_line.toString());
                            out.append(paragraph);
                        }
                        out.append("\n");
                        new_line.setLength(0);
                    }
                } else {
                    new_line.append(line).append("\n");
                }
            }

            if (new_line.length() > 0) {
                new_line.deleteCharAt(new_line.length() - 1);
                String heading = convertToHeading(new_line.toString());
                if (heading != null) {
                    out.append(heading);
                } else {
                    String paragraph = convertToParagraph(new_line.toString());
                    out.append(paragraph);
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при считывании файла:" + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
            bw.write(out.toString());
        } catch (IOException e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}