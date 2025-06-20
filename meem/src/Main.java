import java.util.*;

// Base User class
abstract class User {
    protected String name;
    protected String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    public abstract void displayMenu();
}

// Student class
class Student extends User {
    private int age;
    private String selectedCourse;
    private List<String> selectedSubjects;
    private Map<String, Integer> examResults;
    private boolean isRegistered;

    public Student(String name, String email, int age) {
        super(name, email);
        this.age = age;
        this.selectedSubjects = new ArrayList<>();
        this.examResults = new HashMap<>();
        this.isRegistered = true;
    }

    public int getAge() { return age; }
    public String getSelectedCourse() { return selectedCourse; }
    public List<String> getSelectedSubjects() { return selectedSubjects; }
    public Map<String, Integer> getExamResults() { return examResults; }
    public boolean isRegistered() { return isRegistered; }

    public void setSelectedCourse(String course) { this.selectedCourse = course; }

    public void addSelectedSubject(String subject) {
        if (!selectedSubjects.contains(subject)) {
            selectedSubjects.add(subject);
        }
    }

    public void addExamResult(String subject, int score) {
        examResults.put(subject, score);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== STUDENT MENU ===");
        System.out.println("1. View Available Courses");
        System.out.println("2. Select Course");
        System.out.println("3. Choose Subjects");
        System.out.println("4. Take Exam");
        System.out.println("5. View Exam Results");
        System.out.println("6. Logout");
    }

    @Override
    public String toString() {
        return String.format("Student: %s | Email: %s | Age: %d | Course: %s",
                name, email, age, selectedCourse != null ? selectedCourse : "Not Selected");
    }
}

// Admin class
class Admin extends User {
    public Admin(String name, String email) {
        super(name, email);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. Add Course");
        System.out.println("2. Add Subject to Course");
        System.out.println("3. View All Courses");
        System.out.println("4. View All Students");
        System.out.println("5. View Student Exam Results");
        System.out.println("6. Logout");
    }
}

// Course class
class Course {
    private String courseName;
    private List<String> subjects;

    public Course(String courseName) {
        this.courseName = courseName;
        this.subjects = new ArrayList<>();
    }

    public String getCourseName() { return courseName; }
    public List<String> getSubjects() { return subjects; }

    public void addSubject(String subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    @Override
    public String toString() {
        return courseName + " (Subjects: " + subjects.size() + ")";
    }
}

// Question class for MCQs
class Question {
    private String question;
    private String[] options;
    private int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}

// Main Management System class
class StudentManagementSystem {
    private List<Course> courses;
    private List<Student> students;
    private Admin admin;
    private Scanner scanner;
    private Map<String, List<Question>> subjectQuestions;

    public StudentManagementSystem() {
        courses = new ArrayList<>();
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
        subjectQuestions = new HashMap<>();

        // Initialize admin
        admin = new Admin("Admin", "admin@sms.com");

        // Initialize some default data
        initializeDefaultData();
    }

    private void initializeDefaultData() {
        // Add default courses
        Course javaCourse = new Course("Java Programming");
        javaCourse.addSubject("Core Java");
        javaCourse.addSubject("Advanced Java");
        javaCourse.addSubject("Spring Framework");
        courses.add(javaCourse);

        Course pythonCourse = new Course("Python Programming");
        pythonCourse.addSubject("Python Basics");
        pythonCourse.addSubject("Django Framework");
        pythonCourse.addSubject("Data Science");
        courses.add(pythonCourse);

        Course webCourse = new Course("Web Development");
        webCourse.addSubject("HTML/CSS");
        webCourse.addSubject("JavaScript");
        webCourse.addSubject("React.js");
        courses.add(webCourse);

        // Initialize sample questions
        initializeSampleQuestions();
    }

    private void initializeSampleQuestions() {
        // Java questions
        List<Question> javaQuestions = new ArrayList<>();
        javaQuestions.add(new Question(
                "What is the main feature of Java?",
                new String[]{"Platform Independent", "Fast", "Small", "Simple"}, 0));
        javaQuestions.add(new Question(
                "Which keyword is used for inheritance in Java?",
                new String[]{"implements", "extends", "inherits", "super"}, 1));
        javaQuestions.add(new Question(
                "What is the size of int in Java?",
                new String[]{"2 bytes", "4 bytes", "8 bytes", "1 byte"}, 1));
        javaQuestions.add(new Question(
                "Which collection class allows duplicate elements?",
                new String[]{"Set", "Map", "List", "Queue"}, 2));
        javaQuestions.add(new Question(
                "What is method overloading?",
                new String[]{"Same method name, different parameters", "Different method name", "Same parameters", "None"}, 0));

        subjectQuestions.put("Core Java", javaQuestions);
        subjectQuestions.put("Advanced Java", javaQuestions);
        subjectQuestions.put("Spring Framework", javaQuestions);

        // Python questions
        List<Question> pythonQuestions = new ArrayList<>();
        pythonQuestions.add(new Question(
                "Python is a __ language",
                new String[]{"Compiled", "Interpreted", "Assembly", "Machine"}, 1));
        pythonQuestions.add(new Question(
                "Which symbol is used for comments in Python?",
                new String[]{"//", "/*", "#", "**"}, 2));
        pythonQuestions.add(new Question(
                "What is the correct file extension for Python files?",
                new String[]{".py", ".python", ".p", ".pt"}, 0));
        pythonQuestions.add(new Question(
                "Which keyword is used to define a function in Python?",
                new String[]{"function", "def", "func", "define"}, 1));
        pythonQuestions.add(new Question(
                "What is used to create a list in Python?",
                new String[]{"()", "{}", "[]", "<>"}, 2));

        subjectQuestions.put("Python Basics", pythonQuestions);
        subjectQuestions.put("Django Framework", pythonQuestions);
        subjectQuestions.put("Data Science", pythonQuestions);

        // Web Development questions
        List<Question> webQuestions = new ArrayList<>();
        webQuestions.add(new Question(
                "HTML stands for?",
                new String[]{"Hyper Text Markup Language", "High Tech Modern Language", "Home Tool Markup Language", "None"}, 0));
        webQuestions.add(new Question(
                "Which tag is used for largest heading in HTML?",
                new String[]{"<h6>", "<h1>", "<header>", "<heading>"}, 1));
        webQuestions.add(new Question(
                "CSS stands for?",
                new String[]{"Cascading Style Sheets", "Creative Style Sheets", "Computer Style Sheets", "Colorful Style Sheets"}, 0));
        webQuestions.add(new Question(
                "JavaScript is a __ language",
                new String[]{"Server-side", "Client-side", "Both", "None"}, 2));
        webQuestions.add(new Question(
                "Which method is used to select an element by ID in JavaScript?",
                new String[]{"getElementById", "selectById", "getElement", "findById"}, 0));

        subjectQuestions.put("HTML/CSS", webQuestions);
        subjectQuestions.put("JavaScript", webQuestions);
        subjectQuestions.put("React.js", webQuestions);
    }

    public void start() {
        System.out.println("=== WELCOME TO STUDENT MANAGEMENT SYSTEM ===");

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Registration");
            System.out.println("3. Student Login");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    studentRegistration();
                    break;
                case 3:
                    studentLogin();
                    break;
                case 4:
                    System.out.println("Thank you for using Student Management System!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void adminLogin() {
        System.out.print("Enter admin password (default: admin): ");
        String password = scanner.nextLine();

        if (password.equals("admin")) {
            System.out.println("Admin logged in successfully!");
            adminMenu();
        } else {
            System.out.println("Invalid password!");
        }
    }

    private void adminMenu() {
        while (true) {
            admin.displayMenu();
            System.out.print("Choose option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    addSubject();
                    break;
                case 3:
                    viewAllCourses();
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    viewStudentResults();
                    break;
                case 6:
                    System.out.println("Admin logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void addCourse() {
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        // Check if course already exists
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                System.out.println("Course already exists!");
                return;
            }
        }

        courses.add(new Course(courseName));
        System.out.println("Course added successfully!");
    }

    private void addSubject() {
        if (courses.isEmpty()) {
            System.out.println("No courses available! Please add a course first.");
            return;
        }

        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        System.out.print("Select course (enter number): ");
        int courseIndex = getIntInput() - 1;

        if (courseIndex >= 0 && courseIndex < courses.size()) {
            System.out.print("Enter subject name: ");
            String subjectName = scanner.nextLine();

            courses.get(courseIndex).addSubject(subjectName);
            System.out.println("Subject added successfully!");
        } else {
            System.out.println("Invalid course selection!");
        }
    }

    private void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available!");
            return;
        }

        System.out.println("\n=== ALL COURSES ===");
        for (Course course : courses) {
            System.out.println("Course: " + course.getCourseName());
            System.out.println("Subjects: " + course.getSubjects());
            System.out.println("---");
        }
    }

    private void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered!");
            return;
        }

        System.out.println("\n=== REGISTERED STUDENTS ===");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void viewStudentResults() {
        if (students.isEmpty()) {
            System.out.println("No students registered!");
            return;
        }

        System.out.println("Students:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName());
        }

        System.out.print("Select student (enter number): ");
        int studentIndex = getIntInput() - 1;

        if (studentIndex >= 0 && studentIndex < students.size()) {
            Student student = students.get(studentIndex);
            Map<String, Integer> results = student.getExamResults();

            if (results.isEmpty()) {
                System.out.println("No exam results found for " + student.getName());
            } else {
                System.out.println("\n=== EXAM RESULTS FOR " + student.getName().toUpperCase() + " ===");
                for (Map.Entry<String, Integer> entry : results.entrySet()) {
                    int score = entry.getValue();
                    String status = score >= 60 ? "PASS" : "FAIL";
                    System.out.println("Subject: " + entry.getKey() + " | Score: " + score + "/100 | Status: " + status);
                }
            }
        } else {
            System.out.println("Invalid student selection!");
        }
    }

    private void studentRegistration() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = getIntInput();

        // Check if student already exists
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Student with this email already exists!");
                return;
            }
        }

        Student newStudent = new Student(name, email, age);
        students.add(newStudent);
        System.out.println("Student registered successfully!");
    }

    private void studentLogin() {
        if (students.isEmpty()) {
            System.out.println("No students registered! Please register first.");
            return;
        }

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Student loggedInStudent = null;
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                loggedInStudent = student;
                break;
            }
        }

        if (loggedInStudent != null) {
            System.out.println("Student logged in successfully! Welcome " + loggedInStudent.getName());
            studentMenu(loggedInStudent);
        } else {
            System.out.println("Student not found! Please register first.");
        }
    }

    private void studentMenu(Student student) {
        while (true) {
            student.displayMenu();
            System.out.print("Choose option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                    selectCourse(student);
                    break;
                case 3:
                    chooseSubjects(student);
                    break;
                case 4:
                    takeExam(student);
                    break;
                case 5:
                    viewStudentResults(student);
                    break;
                case 6:
                    System.out.println("Student logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void viewAvailableCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available!");
            return;
        }

        System.out.println("\n=== AVAILABLE COURSES ===");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println((i + 1) + ". " + course.getCourseName());
            System.out.println("   Subjects: " + course.getSubjects());
        }
    }

    private void selectCourse(Student student) {
        if (courses.isEmpty()) {
            System.out.println("No courses available!");
            return;
        }

        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getCourseName());
        }

        System.out.print("Select course (enter number): ");
        int courseIndex = getIntInput() - 1;

        if (courseIndex >= 0 && courseIndex < courses.size()) {
            student.setSelectedCourse(courses.get(courseIndex).getCourseName());
            System.out.println("Course selected successfully: " + courses.get(courseIndex).getCourseName());
        } else {
            System.out.println("Invalid course selection!");
        }
    }

    private void chooseSubjects(Student student) {
        if (student.getSelectedCourse() == null) {
            System.out.println("Please select a course first!");
            return;
        }

        Course selectedCourse = null;
        for (Course course : courses) {
            if (course.getCourseName().equals(student.getSelectedCourse())) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse == null || selectedCourse.getSubjects().isEmpty()) {
            System.out.println("No subjects available for this course!");
            return;
        }

        System.out.println("Available Subjects in " + selectedCourse.getCourseName() + ":");
        List<String> subjects = selectedCourse.getSubjects();
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }

        System.out.print("Enter subject numbers to select (comma-separated, e.g., 1,2,3): ");
        String input = scanner.nextLine();
        String[] selections = input.split(",");

        for (String selection : selections) {
            try {
                int subjectIndex = Integer.parseInt(selection.trim()) - 1;
                if (subjectIndex >= 0 && subjectIndex < subjects.size()) {
                    student.addSelectedSubject(subjects.get(subjectIndex));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + selection);
            }
        }

        System.out.println("Selected subjects: " + student.getSelectedSubjects());
    }

    private void takeExam(Student student) {
        if (student.getSelectedSubjects().isEmpty()) {
            System.out.println("Please select subjects first!");
            return;
        }

        System.out.println("Available subjects for exam:");
        List<String> subjects = student.getSelectedSubjects();
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }

        System.out.print("Select subject for exam (enter number): ");
        int subjectIndex = getIntInput() - 1;

        if (subjectIndex >= 0 && subjectIndex < subjects.size()) {
            String selectedSubject = subjects.get(subjectIndex);
            conductExam(student, selectedSubject);
        } else {
            System.out.println("Invalid subject selection!");
        }
    }

    private void conductExam(Student student, String subject) {
        List<Question> questions = subjectQuestions.get(subject);
        if (questions == null || questions.isEmpty()) {
            System.out.println("No questions available for this subject!");
            return;
        }

        System.out.println("\n=== EXAM: " + subject.toUpperCase() + " ===");
        System.out.println("Instructions: Answer all 5 questions. Each question carries 20 marks.");
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        int score = 0;
        Random random = new Random();
        List<Question> examQuestions = new ArrayList<>();

        // Select 5 random questions
        for (int i = 0; i < 5; i++) {
            examQuestions.add(questions.get(random.nextInt(questions.size())));
        }

        for (int i = 0; i < examQuestions.size(); i++) {
            Question q = examQuestions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.getQuestion());

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }

            System.out.print("Your answer (1-4): ");
            int answer = getIntInput() - 1;

            if (q.isCorrect(answer)) {
                score += 20;
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong! Correct answer: " + options[q.getCorrectAnswer()]);
            }
        }

        student.addExamResult(subject, score);
        String status = score >= 60 ? "PASS" : "FAIL";

        System.out.println("\n=== EXAM COMPLETED ===");
        System.out.println("Subject: " + subject);
        System.out.println("Score: " + score + "/100");
        System.out.println("Status: " + status);

        if (score >= 60) {
            System.out.println("Congratulations! You passed the exam!");
        } else {
            System.out.println("Better luck next time! You need 60% to pass.");
        }
    }

    private void viewStudentResults(Student student) {
        Map<String, Integer> results = student.getExamResults();

        if (results.isEmpty()) {
            System.out.println("No exam results found! Please take an exam first.");
            return;
        }

        System.out.println("\n=== YOUR EXAM RESULTS ===");
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            int score = entry.getValue();
            String status = score >= 60 ? "PASS" : "FAIL";
            System.out.println("Subject: " + entry.getKey() + " | Score: " + score + "/100 | Status: " + status);
        }
    }

    private int getIntInput() {
        try {
            int input = Integer.parseInt(scanner.nextLine());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.start();
    }
}