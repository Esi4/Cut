package ru.spbstu;

import java.io.*;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.*;

public class Cut {

    @Option(name = "-o", metaVar = "OutputName", usage = "OutputName")
    private File outputFile;

    @Option(name = "-w", metaVar = "IndentWords", usage = "Indent parameter in words", forbids = {"-c"})
    private boolean words;

    @Option(name = "-c", metaVar = "IndentChars", usage = "Indent parameter in chars", forbids = {"-w"})
    private boolean chars;

    @Argument(required = true, metaVar = "Range", usage = "Spacing between words or characters")
    private String range;

    @Argument(metaVar = "InputName", index = 1, usage = "InputName")
    private File inputFile;

    public static void main(String[] args) throws IOException {
        new Cut().launch(args);
    }

    private void launch(String[] args) throws IOException {
        int start;
        int end = Integer.MAX_VALUE;

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println(" cut [-c | -w] [-o] [InputName] range");
            parser.printUsage(System.err);
            return;
        }

        if (!words && !chars) {
            System.err.print("-w or -c argument is required");
            return;
        }

        if (!range.matches("^(\\d*)-(\\d*)$")) {
            System.err.println("Invalid water format. The correct format is number-number or -number, number-.");
            return;
        }

        String[] split = range.split("-");
        if (split[0].equals("")) {
            start = 0;
        } else if (split.length == 2) {
            start = Integer.parseInt(split[0]);
            end = Integer.parseInt(split[1]);
        } else start = Integer.parseInt(split[0]);

        if (start > end) {
            System.err.print("Start value is greater than the end");
            return;
        }

        Cutter cutter = new Cutter(start, end);
        String line;
        List<String> list = new ArrayList<>();

        if(inputFile != null ) {
            try (BufferedReader read = new BufferedReader(new FileReader(inputFile))) {
                line = read.readLine();
                while (line.equals("")) {
                    list.add(line);
                    line = read.readLine();
                }
            }
        }
         else {
             Scanner scan = new Scanner(System.in);
             line = scan.nextLine();
             while (!line.equals("/stop")) {
                    list.add(line);
                    line = scan.nextLine();
             }
         }

         if(words) {
             list = cutter.words(list);
         } else {
             list = cutter.chars(list);
         }

         if (outputFile != null) {
             int x = list.size();
             try (BufferedWriter write = new BufferedWriter((new FileWriter(outputFile)))) {
                 for (int j = 0; j < x - 1; j++) {
                     write.write(list.get(j) + System.lineSeparator());
                 }
                 if (x > 0) {
                     write.write(list.get(x -1));
                 }
             }
         }
    }
}