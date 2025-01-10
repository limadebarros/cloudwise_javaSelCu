package report;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CucumberReport {
    public static void generateReport() {
        // Specify the output directory for the report
        File reportOutputDirectory = new File("target");

        // Specify the JSON files to be used for the report
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber.json");

        // Create a Configuration object
        String projectName = "Cloudwise"; // Change this to your project name
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);

        // Create the ReportBuilder with the JSON files and configuration
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
