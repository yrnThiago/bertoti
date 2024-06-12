package pom.xml.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;

public class FileReader {
    public static String convertTextFileToString (String filePath) throws IOException {
        ClassLoader classLoader = OllamaAPI.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);
        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);
            StringBuilder stringBuffer = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuffer.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            
            return stringBuffer.toString();
        } else {
            throw new IOException("Sample database question file not found.");
        }

    } 
}

