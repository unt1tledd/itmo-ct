package Sc;

import java.io.*;

public class MyScanner implements AutoCloseable {
    private Reader reader;
    private char[] buffer = new char[1024];
    private StringBuilder line = new StringBuilder();
    private int pos = 0;
    private int sizeBuffer = 0;
    private boolean end = false;
    private StringBuilder num = new StringBuilder();
    private boolean hasline = false;
    boolean isNegative = false;
    private static final String lineSeparator = System.lineSeparator();
    private int separatorPos = 0;

    public MyScanner(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
    }

    public MyScanner(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public MyScanner(String input) {
        this.reader = new BufferedReader(new StringReader(input));
    }

    private boolean isSeparator(char ch) {
        if (separatorPos < lineSeparator.length() && ch == lineSeparator.charAt(separatorPos)) {
            separatorPos++;
            if (separatorPos == lineSeparator.length()) {
                separatorPos = 0;
                return true;
            }
        } else {
            separatorPos = 0;
        }
        return false;
    }

    private void skipWhitespace() throws IOException {
        while (!end) {
            if (pos >= sizeBuffer) {
                fillBuffer();
                if (end) {
                    break;
                }
            }
            if (pos < sizeBuffer && !Character.isWhitespace(buffer[pos])) {
                break;
            }
            pos++;
        }
    }

    public boolean hasNext() throws IOException {
        skipWhitespace();
        return !end && !isSeparator(buffer[pos]);
    }

    private void fillBuffer() throws IOException {
        sizeBuffer = reader.read(buffer);
        if (sizeBuffer == -1) {
            end = true;
        } else {
            pos = 0;
        }
    }

    /*public int getLineNumber() {
        return lineNumber;
    }

    public int getWordPos() {
        return wordPos;
    }*/

    public String next() throws IOException {
        StringBuilder str = new StringBuilder();
        while (!end) {
            if (pos >= sizeBuffer) {
                fillBuffer();
                if (end) {
                    break;
                }
            }

            char ch = buffer[pos];

            if (Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch)|| ch == '\'') {
                str.append(ch);
                pos++;
            } else if (isSeparator(ch) || Character.isWhitespace(ch)) {
                /*if (isSeparator(ch)) {
                    wordPos = 0;
                    lineNumber++;
                }*/
                break;
            } else if (str.length() == 0){
                pos++;
            } else {
                break;
            }
        }
        if (!str.isEmpty()) {
            //wordPos++;
            return str.toString();
        }
        return null;
    }

    public boolean hasNextLine() throws IOException {
        if (!hasline) {
            line.setLength(0);
            while (!end) {
                if (pos >= sizeBuffer) {
                    fillBuffer();
                    if (end) {
                        break;
                    }
                }
                char ch = buffer[pos++];
                if (isSeparator(ch)) {
                    hasline = true;
                    break;
                } else {
                    line.append(ch);
                }
            }
            if (!end && line.isEmpty()) {
                hasline = true;
            }
        }
        return hasline;
    }

    public String nextLine() throws IOException {
        if (hasNextLine()) {
            hasline = false;
            return line.toString();
        }
        return null;
    }

    public boolean hasNextInt() throws IOException {
        num.setLength(0);
        while (!end) {
            if (pos >= sizeBuffer) {fillBuffer();
                if (end) break;
            }
            char ch = buffer[pos];

            if (Character.isDigit(ch)) {
                num.append(ch);
            } else if (ch == '-') {
                if (num.length() == 0 && !isNegative) {
                    isNegative = true;
                } else {
                    break;
                }
            } else if (Character.isWhitespace(ch) || isSeparator(ch)) {
                pos++;
                break;
            }
            pos++;
        }

        if (num.isEmpty()) {
            System.err.println(num.toString());
            return false;
        }

        return true;
    }

    public int nextInt() throws IOException {
        if (!num.isEmpty()) {
            System.err.println(num.toString());
            return Integer.parseInt(num.toString());
        }
        throw new NumberFormatException("Invalid number format");
    }

    public void close() throws IOException {
        reader.close();
    }
}