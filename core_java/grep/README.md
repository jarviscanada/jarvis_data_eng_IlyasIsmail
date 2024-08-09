# Introduction
The Grep App is a Java application that mimics the "grep" command from Linux. It takes in a regular expression pattern and outputs a text file with all the lines containing the given pattern from every .txt file in a specified directory. The technologies used to achieve this was Java and its many packages such as java.io and java.util.stream to receive, read, write to, and output txt files using lambda expressions utilizing streams. Maven was used to compile, package, and get all the packages needed for the app. Docker was used to create and upload an image of the Grep App for users to easily obtain and use the app.

# Quick Start

Once you've downloaded the Docker image, input the command: <br /> <br /> docker run --rm -v {input_absolute_path} -v {output_absolute_path}  ilyasismaiil/grep {pattern} {input_relative_path} {output_relative_path} <br />  <br />Replace the placeholders with the appropriate value:
- {input_absolute_path} = absolute path for the directory containing the files to be scanned. 
- {output_absolute_path} = absolute path for the directory that will contain the outputted results.
- {pattern} = the regular expression pattern the app will be searching for. 
- {input_relative_path} = relative path for the directory containing the files to be scanned.
- {output_relative_path} = relative path for the directory that will contain the output file.

# Implementation
The program will search through every .txt file in the given directory. It will then produce an array containing each line from the file it is processing. For each line, it will check to see if the line contains the given pattern. If the line contains a pattern, it will add it to an array to be outputted. Once every file has been processed, a file will be produced containing all the lines that were a match.
## Pseudocode
```
public void process() throws IOException {
        ArrayList<String> matchedLines = new ArrayList<String>();

        for (File file : listFiles(rootPath))
            for (String line : readLines(file))
                if (containsPattern(line))
                    matchedLines.add(line);

        writeToFile(matchedLines);
    }
```

## Performance Issues
The application cannot handle files that are too large. This could be solved by returning streams and writing the file based on that instead of saving the lines into an array list causing the application to run out of memory.

# Test
The application was tested manually by using different regex patterns on prepared sample data and unique file types.  

# Deployment
Deployment was done through Docker. A Dockerfile was created to build an image. The image was then pushed onto Docker Hub for users to easily consume.

# Improvement
- Streams can be better utilized, allowing for larger files to be processed. <br />
- An option can be added to allow the program to look into subdirectories.
- The output can be improved by informing the user which pattern came from which file.