package ru.spbstu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cutter {
    private final int start;
    private final int end;
    private String str;
    private StringBuilder strB = new StringBuilder();

    public Cutter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public void distribution(BufferedWriter write, BufferedReader read, boolean lever) throws IOException {
        try {
            while ((str = read.readLine()) != null) {
                if (lever) {
                    strB.append(words(str));
                } else strB.append(chars(str));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        write.write(strB.toString());
    }

    public String words(String str) {
        StringBuilder strB = new StringBuilder();
        String[] array;
        array = str.split(" ");
        int x = array.length - 1;
        int min = Math.min(end, x);
        for (int j = start; j < min; j++) {
            strB.append(array[j]);
            strB.append(" ");
        }
        strB.append(System.lineSeparator());
        return strB.toString();
    }

    public String chars(String str) {
        StringBuilder strB = new StringBuilder();
        int x = str.length() - 1;
        int min = Math.min(end, x);
        for (int j = start; j < min; j++) {
            strB.append(str.charAt(j));
        }
        strB.append(System.lineSeparator());
        return strB.toString();
    }
}