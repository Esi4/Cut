package ru.spbstu;

import java.util.ArrayList;
import java.util.List;

public class Cutter {
     private final int start;
    private final int end;

    public Cutter(int start, int end) {
        this.start = start;
        this.end = end;
    }
    public List<String> words(List<String> list) {
        ArrayList<String> res = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        String[] array;
        for (String line : list) {
            array = line.split(" ");
            int x = array.length;
            int min = Math.min(end, array.length);
            if (start <= x) {
                for (int j = start - 1; j < min; j++) {
                    str.append(array[j]).append(" ");
                    str.deleteCharAt(str.length() - 1);
                }
            }
            res.add(str.toString());
            str = new StringBuilder();
        }
        return res;
    }

    public List<String> chars(List<String> list) {
        ArrayList<String> res = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for(String line : list) {
            int x = line.length();
            int min = Math.min(end, x);
            if (x > start) {
                str.append(line, start - 1, min);
            }
            res.add(str.toString());
            str = new StringBuilder();
        }
        return res;
    }
}
