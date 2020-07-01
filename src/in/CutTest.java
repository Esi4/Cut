package in;

import org.junit.jupiter.api.Test;
import ru.spbstu.Cut;

import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;


class CutTest {

    private final Path inputFile = Paths.get("src","in", "inputtext");
    private final Path outputFile = Paths.get("src","in", "outputtext");
    private final String ls = System.lineSeparator();
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();


    @Test
    void File() throws IOException {
        Cut.main(new String[]{"-w", "-o", outputFile.toString(), "-r", "1-4", inputFile.toString()});
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
        ByteArrayInputStream input = new ByteArrayInputStream(("Внимание внимание"
                + ls + "а я вас вижу").getBytes());
        System.setIn(input);
        Cut.main(new String[]{"-c", "-r", "1-2"});
        assertEquals("Вн" + ls + "а " + ls, baos.toString());
    }

    @Test
    void error() throws IOException {
        PrintStream output = new PrintStream(baos);
        System.setErr(output);
        Cut.main(new String[]{"-w", "-o", outputFile.toString(),"-r", "1---4", inputFile.toString()});
        assertEquals("Invalid water format. The correct format is number-number or -number, number-." + ls, baos.toString());
    }
}

