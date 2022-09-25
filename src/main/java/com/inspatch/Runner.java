package com.inspatch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.inspatch.helper.JsonSerializationException;
import com.inspatch.helper.ObjectToJsonConverter;
import com.inspatch.model.Report;
import com.inspatch.model.Sequence;

public class Runner {

    private static void generateJsonFile(Report report, String path) throws JsonSerializationException, IOException {
        ObjectToJsonConverter serializer = new ObjectToJsonConverter();
        String jsonString = serializer.convertToJson(report);
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(path), false))) {
            writer.print(jsonString);
        }
    }

    private static void generateJsFile(Report report, String path) throws JsonSerializationException, IOException {
        ObjectToJsonConverter serializer = new ObjectToJsonConverter();
        String jsString = "export var sequences = " + serializer.convertToJson(report) + ";";
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(path), false))) {
            writer.print(jsString);
        }
    }

    public static void main(String[] args) throws JsonSerializationException, IOException {
        Report report = new Report("12/05/1999", "606", "202", "404",
                new Sequence[] { 
                    new Sequence("CAHOS", "path/to/agdal"),
                    new Sequence("DSLY", "path/to/ensias"),
                    new Sequence("IGNEMA", "path/to/kenitra")
                });
        String rootPath = Runner.class.getClassLoader().getResource("test.json").getPath().split("target/classes/test.json")[0] + "src/main/resources/";
        String pathToJson = rootPath + "test.json";
        String pathToJs = rootPath + "test.js";
        generateJsonFile(report, pathToJson);
        generateJsFile(report, pathToJs);
    }
}
