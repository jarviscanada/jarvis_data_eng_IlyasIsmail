package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    @Override
    public void process() throws IOException {
        ArrayList<String> matchedLines = new ArrayList<String>();

        for (File file : listFiles(rootPath))
            for (String line : readLines(file))
                if (containsPattern(line))
                    matchedLines.add(line);

        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {

        File dir = new File(rootDir);

        return Arrays.asList(dir.listFiles());
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<String>();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("This file does not exist!", e);
        } catch (IOException e) {
            throw new RuntimeException("There has been an input error!", e);
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        for (String line : lines) {
            bufferedWriter.write(line + "\n");
        }
        bufferedWriter.close();
    }

    public static void main(String[] args) {
        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);
        try {
            javaGrepImp.process();
        } catch (Exception ex) {
            javaGrepImp.logger.error("Error!", ex);
        }

    }
}
