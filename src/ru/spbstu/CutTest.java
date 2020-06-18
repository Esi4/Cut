package ru.spbstu;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;


class CutTest {

    private Path inputFile = Paths.get("src","in", "inputtext.txt");
    private Path outputFile = Paths.get("src","in", "outputtext.txt");
    private String ls = System.lineSeparator();
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();


    @Test
    void File() throws IOException {
        Cut.main(new String[]{"-w", "-o", outputFile.toString(), "1-4", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String x = reader.readLine();
        assertEquals("Я кричал - вы ", x);
        x = reader.readLine();
        assertEquals("Что ж вы уронили ", x);
        x = reader.readLine();
        assertEquals("А мне сказали в ", x);
        x = reader.readLine();
        assertEquals("Вот, говорят, прекрасно, ты ", x);
        reader.close();

    }

    @Test
    void Console() throws IOException {
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        ByteArrayInputStream input = new ByteArrayInputStream(("Внимание внимание" + ls +
                "а я вас вижу" + ls + "/stop").getBytes());
        System.setIn(input);
        Cut.main(new String[]{"-c", "1-2"});
        assertEquals("ниман" + ls + " я ва" + ls, baos.toString());
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    void error() throws IOException {
        PrintStream output = new PrintStream(baos);
        System.setErr(output);
        Cut.main(new String[]{"-w", "-o", outputFile.toString(), "1---4", inputFile.toString()});
        assertEquals("Invalid water format. The correct format is number-number or -number, number-." + ls, baos.toString());
    }
}

