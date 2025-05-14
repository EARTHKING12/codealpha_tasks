import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private ArrayList<Double> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addGrade(double grade) {
        grades.add(grade);
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0.0;

        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public void displayGrades() {
        System.out.println("Grades for " + name + ": " + grades.toString());
        System.out.printf("Average: %.2f%n", getAverage());
    }
}

public class StudentGradeTracker {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new Student(name));
        System.out.println("Student added.");
    }

    private static void enterGrades() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                System.out.print("How many grades to enter? ");
                int count = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < count; i++) {
                    System.out.print("Enter grade #" + (i + 1) + ": ");
                    double grade = Double.parseDouble(scanner.nextLine());
                    student.addGrade(grade);
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void showAllStudents() {
        for (Student student : students) {
            student.displayGrades();
            System.out.println("------------------");
        }
    }

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\nStudent Grade Tracker");
            System.out.println("1. Add Student");
            System.out.println("2. Enter Grades");
            System.out.println("3. Show All Students");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addStudent(); break;
                case 2: enterGrades(); break;
                case 3: showAllStudents(); break;
                case 4: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }
}
