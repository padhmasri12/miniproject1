
public class Student {

    private String name;
    private String regnum;
    private String department;

    public Student(String name, String regnum, String department) {

        this.name = name;
        this.regnum = regnum;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getRegnum() {
        return regnum;
    }

    public String getDepartment() {
        return department;
    }


}