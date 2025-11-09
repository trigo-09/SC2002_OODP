package controller.database;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;



public class CsvLoader {
    private String studentListDirectory;
    private String staffListDirectory;
    private String repListDirectory;

    public void setStudentListDirectory(String studentList) {this.studentListDirectory = studentList;}
    public void setStaffListDirectory(String staffList) {this.staffListDirectory = staffList;}
    public void setRepListDirectory(String repList) {this.repListDirectory = repList;}


    public String getStudentList() {
        return studentListDirectory;
    }
    public String getStaffList() {
        return staffListDirectory;
    }
    public String getRepList() {
        return repListDirectory;
    }

    public List<Student> studentLoader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(studentList));
        List<Student> students = new List<Student>;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            for (String value : line){
                i++
                switch (i){
                    case 1:
                        String id = value;
                        break;
                    case 2:
                        String name = value;
                        break;
                    case 3:
                        String major = value;
                        break;
                    case 4:
                        int year = value;
                        break;
                    case 5:
                        i = 0;
                        break;
                }
            }
            Student = new Student(name,id,"Password",year,major);
            students.add(Student);
        }
        return students;
    }
    public List<CareerStaff> staffLoader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(staffListDirectory));
        List<CareerStaff> staffs = new List<CareerStaff>;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            for (String value : line){
                i++
                switch (i){
                    case 1:
                        String id = value;
                        break;
                    case 2:
                        String name = value;
                        break;
                    case 3:
                        break;
                    case 4:
                        String department = value;
                        break;
                    case 5:
                        i = 0;
                        break;
                }
            }
            staff = new Staff(name,id,"Password",department);
            staffs.add(staff);
        }
        return staffs;
    }
    public List<CompanyRep> repLoader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(repListDirectory));
        List<CompanyRep> reps = new List<CompanyRep>;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            for (String value : line){
                i++
                switch (i){
                    case 1:
                        String id = value;
                        break;
                    case 2:
                        String name = value;
                        break;
                    case 3:
                        String company = value;
                        break;
                    case 4:
                        String department = value;
                        break;
                    case 5:
                        String position = value;
                        break;
                    case 6:
                        break;
                    case 7:
                        String status = value;
                        i = 0;
                        break;
                }
            }
            rep = new CompanyRep(name,id,"Password",company,department,position);
            reps.add(rep);
        }
        return reps;
    }
}
