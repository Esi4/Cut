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

    @Option(name = "-w", metaVar = "IndentWords", usage = "Indent parameter inputtext words", forbids = {"-c"})
    private boolean words;

    @Option(name = "-c", metaVar = "IndentChars", usage = "Indent parameter inputtext chars", forbids = {"-w"})
    private boolean chars;

    @Option(name = "-r", required = true, metaVar = "Range", usage = "Spacing between words or characters")
    private String range;

    @Argument(metaVar = "InputName", usage = "InputName")
    private File inputFile;

    public static void main(String[] args) throws IOException {
        new Cut().launch(args);
    }

    private void launch(String[] args) throws IOException {
        int start = 0;
        int end = 0;

        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("cut [-c | -w] [-o] [InputName] range");
            parser.printUsage(System.err);
            return;
        }

        if (!words && !chars) {
            System.err.print("-w or -c argument is required");
            return;
        }


        if (!range.matches("^(\\d*-\\d*)|(\\d*-)|(-\\d*)$")) {
            System.err.println("Invalid water format. The correct format is number-number or -number, number-.");
            return;
        }

        String[] split = range.split("-");
         if (split[0].equals("")) {
            start = 0;
        } else if (split.length == 1) {
            start = Integer.parseInt(split[0]);
            end = Integer.MAX_VALUE;
        } else {
            end = Integer.parseInt(split[1]);
        }

        if (start > end) {
            System.err.print("Start value is greater than the end");
            return;
        }

        Cutter cutter = new Cutter(start, end);

        BufferedWriter write;
        BufferedReader read;
        try {
            if (inputFile != null) {
                read = new BufferedReader((new FileReader(inputFile)));
            } else read = new BufferedReader(new InputStreamReader(System.in));

            if (outputFile != null) {
                write = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
            } else write = new BufferedWriter(new OutputStreamWriter(System.out));

            cutter.distribution(write, read, words);
            write.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
