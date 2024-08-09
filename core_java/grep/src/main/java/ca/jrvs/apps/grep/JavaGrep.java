package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * Top level search workflow
     * @throws IOException
     */
    void process() throws IOException;

    /**
     * Travel through the directory and return all files
     * @param rootDir directory to travel through
     * @return files from rootDir
     */
    List<File> listFiles(String rootDir);

    /**
     * Return all the lines from a file
     * @param inputFile file to be read
     * @return all the lines
     * @throws IllegalArgumentException if inputFile is not a file
     */
    List<String> readLines(File inputFile);

    /**
     * Checks if the line contains a given regex pattern
     * @param line string to be checked
     * @return true if string contains pattern
     */
    boolean containsPattern(String line);

    /**
     * Write lines to file
     * @param lines all matched lines
     * @throws IOException if write failed
     */
    void writeToFile(List<String> lines) throws IOException;

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}
