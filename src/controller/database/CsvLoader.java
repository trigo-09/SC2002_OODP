package controller.database;

import entity.user.*;
import java.io.*;
import java.util.*;

public class CsvLoader {

    private final String directoryPath;
    private final IRepository sysRepo;

    public CsvLoader(IRepository sysRepo, String directoryPath) {
        this.sysRepo = sysRepo;
        this.directoryPath = directoryPath;
    }

    public CsvLoader(IRepository sysRepo) {
        this.sysRepo = sysRepo;
        this.directoryPath = "data/";
    }
    /**
     * Load all CSV files from a directory and populate the SystemRepository.
     *
     */
    public IRepository load() {

        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Invalid directory: " + directoryPath);
            return sysRepo;
        }

        File[] csvFiles = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            System.err.println("No CSV files found in directory: " + directoryPath);
            return sysRepo;
        }

        for (File file : csvFiles) {
            System.out.println("[INFO] Processing file: " + file.getName());
            loadSingleFile(file);
        }

        System.out.println("[INFO] Directory load complete. "
                + sysRepo.getStudents().size() + " students, "
                + sysRepo.getCareerStaff().size() + " staff, "
                + sysRepo.getPendingReps().size() + " reps loaded.");

        return sysRepo;
    }

    /**
     * Load a CSV file.
     *
     * @param file path to CSV file
     */
    public void loadSingleFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine();
            if (header == null || header.isBlank()) {
                System.err.println("[ERROR] Empty file: " + file);
            }

            String[] headerParts = header.split(",");
            String headerLine = String.join(",", headerParts).toLowerCase();

            if (headerLine.contains("studentid")) {
                loadStudents(reader);
            } else if (headerLine.contains("staffid")) {
                loadStaff(reader);
            } else {
                System.err.println("[WARN] Unrecognized file type for: " + file);
            }

        } catch (IOException e) {
            System.err.println("[ERROR] Failed to read file: " + file);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected error while loading file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    // === Student Loader ===
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
                System.err.println("[WARN] Invalid year format at line " + lineCount);
            } catch (Exception e) {
                System.err.println("[WARN] Skipping bad student entry at line " + lineCount + ": " + e.getMessage());
            }
        }
    }

    // === Staff Loader ===
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
                System.err.println("Skipping bad staff entry at line " + lineCount + ": " + e.getMessage());
            }
        }
    }


}
