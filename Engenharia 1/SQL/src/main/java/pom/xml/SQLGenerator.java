package pom.xml;

import io.github.amithkoujalgi.ollama4j.core.utils.OptionsBuilder;

import java.util.Scanner;

import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.models.OllamaResult;


public class SQLGenerator {
    private String databaseSchema = AppConfig.getDbSchema();

    public String getSQL(String question, String databaseSchema) {
        String sqlQuery = "";
        String ollamaUrl= AppConfig.getOllamaUrl();
        
        try {
            OllamaAPI ollamaAPI = new OllamaAPI(ollamaUrl);
            ollamaAPI.setRequestTimeoutSeconds(1000);

            String prompt = databaseSchema.replace("<question>", question);
            
            OllamaResult result =
                    ollamaAPI.generate("natural_language_to_sql", prompt,
                        new OptionsBuilder()
                        .build()
                    );

            sqlQuery = result.getResponse();
            
        } catch (Exception e) {
            System.out.println(e);
        }

        return sqlQuery;

    }

    public static void main(String[] args) {
        SQLGenerator sqlGenerator = new SQLGenerator();
        Scanner scanner = new Scanner(System.in);

        System.out.printf("\n\nQual a sua pergunta: ");
        String question = scanner.nextLine();

        String result = sqlGenerator.getSQL(question, sqlGenerator.databaseSchema);

        System.out.println("\n\nPergunta:\n- " + question);
        System.out.println("SQL Query:\n- " + result);
    }

}