package controller.database;

import entity.user.*;
import util.FindDataDirectory;
import java.io.*;
import java.util.*;

/**
 * CSVLoader read CSV to create relevant objects which are added to repository
 */
public class CsvLoader {

    private final String directoryPath;
    private final IRepository sysRepo;

    /**
     * constructor of csvloader if file dir specified
     * @param sysRepo system repository
     * @param directoryPath file directory path
     */
    public CsvLoader(IRepository sysRepo, String directoryPath) {
        this.sysRepo = sysRepo;
        this.directoryPath = directoryPath;
    }

    /**
     * constructor of CSVLoader with predefined file dir
     * @param sysRepo system repository
     */
    public CsvLoader(IRepository sysRepo) {
        this.sysRepo = sysRepo;
        this.directoryPath = FindDataDirectory.findDataDirectory().toString();
    }
    /**
     * Load all CSV files from a dir and add to Repository.
     */
    public IRepository load() {

        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Invalid dir: " + directoryPath);
            return sysRepo;
        }

        File[] csvFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            System.err.println("No csv file found: " + directoryPath);
            return sysRepo;
        }

        for (File file : csvFiles) {
            System.out.println("Process file: " + file.getName());
            loadSingleFile(file);
        }

        return sysRepo;
    }

    /**
     * Load a CSV file.
     * @param file path to CSV file
     */
    public void loadSingleFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine();
            if (header == null || header.isBlank()) {
                System.err.println("error empty file: " + file);
            }

            String[] headerParts = header.split(",");
            String headerLine = String.join(",", headerParts).toLowerCase();

            if (headerLine.contains("studentid")) {
                loadStudents(reader);
            } else if (headerLine.contains("staffid")) {
                loadStaff(reader);
            } else {
                System.err.println("Unrecognised file: " + file);
            }

        } catch (IOException e) {
            System.err.println("error failed to read file: " + file);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * read student CSV and create student object
     * add student object to repository
     * @param reader buffered reader
     * @throws IOException if failed to read csv line
     */
    private void loadStudents(BufferedReader reader) throws IOException {
        System.out.println("Loading student list...");
        String line;
        int lineCount = 0;

        while ((line = reader.readLine()) != null) {
            lineCount++;
            try {
                String[] values = line.split(",");
                if (values.length < 4) continue; // skip malformed rows

                String id = values[0].trim();
                String name = values[1].trim();
                String major = values[2].trim();
                String year = values[3].trim();

                Map<String, String> attr = new HashMap<>();
                attr.put("major", major);
                attr.put("year", year);

                Student student = (Student) UserFactory.createUser(UserRole.STUDENT, id, name, "password", attr);
                sysRepo.addStudent(student);
            } catch (NumberFormatException e) {
                System.err.println("Invalid year format at line " + lineCount);
            } catch (Exception e) {
                System.err.println("Skipped line " + lineCount + ": " + e.getMessage());
            }
        }
    }

    /**
     * read staff CSV and create staff object
     * add staff object to repository
     * @param reader buffered reader
     * @throws IOException if failed to read csv line
     */
    private void loadStaff(BufferedReader reader) throws IOException {
        System.out.println("Loading staff list...");
        String line;
        int lineCount = 0;

        while ((line = reader.readLine()) != null) {
            lineCount++;
            try {
                String[] values = line.split(",");
                if (values.length < 4) continue;

                String id = values[0].trim();
                String name = values[1].trim();
                String department = values[3].trim(); // skip Role and ignore Email

                Map<String, String> attr = new HashMap<>();
                attr.put("department", department);

                CareerStaff staff = (CareerStaff) UserFactory.createUser(UserRole.STAFF, id, name, "password", attr);
                sysRepo.addCareerStaff(staff);
            } catch (Exception e) {
                System.err.println("Skipping line " + lineCount + ": " + e.getMessage());
            }
        }
    }
}
