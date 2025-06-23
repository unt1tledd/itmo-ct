package Sc;

import java.util.Arrays;

public class IntList {
    private String[] data;
    private int size;
    private final int start_buffer = 5;

    public IntList() {
        data = new String[start_buffer];
        size = 0;
    }

    public void add(String value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
        data[size++] = value;
    }

    public int size() {
        return size;
    }

    public String get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return data[index];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public String toString2ver() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1 ) {
                if (i % 2 == 0) {
                sb.append(":");
                } else {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}