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

    public SystemRepository loadtoRepo() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(studentList));
        SystemReposistory sysRepo = new SystemReposistory();
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
            sysRepo.addStudent(Student);
        }
        BufferedReader reader = new BufferedReader(new FileReader(staffListDirectory));
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
            sysRep.addCareerStaff(staff);
        }
        return sysRepo;
    }
}
