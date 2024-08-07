package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    //Getters and Setters
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

    //parent method which will run all the methods needed for desired result
    @Override
    public void process() throws IOException {
        ArrayList<String> matchedLines = new ArrayList<String>();

        for (File file : listFiles(rootPath))
            for (String line : readLines(file))
                if (containsPattern(line))
                    matchedLines.add(line);

        writeToFile(matchedLines);
    }

    //return a list of all the files in the given rootDir
    @Override
    public List<File> listFiles(String rootDir) {

        File dir = new File(rootDir);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        };
        return Arrays.asList(dir.listFiles(filenameFilter));
    }

    //return a String list of all the lines in the given text file
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

    //return a boolean based on the regex pattern and inputted line
    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    //create a new txt file with all the lines that contain a matching pattern in the given outFile path
    @Override
    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile));
        for (String line : lines) {
            bufferedWriter.write(line + "\n");
        }
        bufferedWriter.close();
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

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
