package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        BasicConfigurator.configure();

        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            javaGrepLambdaImp.logger.error("Error!", ex);
        }
    }

    //return a list of files in the given rootDir
    @Override
    public List<File> listFiles(String rootDir) {
        try (Stream<Path> stream = Files.list(Paths.get(rootDir))) {
            return stream
                    .filter(file -> Files.isDirectory(Paths.get(rootDir)))
                    .map(Path::toFile)
                    .filter(f -> f.getName().endsWith(".txt"))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            logger.error("IO Error! Returning an empty list.", ex);
            return new ArrayList<>();
        }
    }

    //return a String list with all the lines in a txt file
    @Override
    public List<String> readLines(File inputFile) {
        try (Stream<String> stream = Files.lines(Paths.get(inputFile.getAbsolutePath()))) {
            return stream
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            logger.error("IO Error! Returning an empty list.", ex);
            return new ArrayList<>();
        }
    }
}
