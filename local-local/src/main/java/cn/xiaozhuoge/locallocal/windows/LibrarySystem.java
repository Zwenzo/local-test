package cn.xiaozhuoge.locallocal.windows;


import java.text.Collator;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author zhaowz
 * @date 2023/6/15 10:48
 */
public class LibrarySystem {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StudentAll studentAll = new StudentAll();
        allCourse allCourse = new allCourse();
        Scores cj = new Scores();
        String s = input.nextLine();
        String[] str = s.split(" ");
        //必修1，选修2/考试1，考察2
        while (!(str[0].equals("end"))) {
            if (isNumeric(str[0])) {
                if (str[0].length() != 8 || str[1].length() > 10) {
                    System.out.println("wrong format");
                } else if (str.length == 4 && !isNumeric(str[3])) {
                    System.out.println("wrong format");
                } else if (str.length == 5 && !isNumeric(str[3]) && !isNumeric(str[3])) {
                    System.out.println("wrong format");
                } else if (str.length == 4 && (Integer.parseInt(str[3]) < 0 || Integer.parseInt(str[3]) > 100)) {
                    System.out.println("wrong format");
                } else if (str.length == 5 && (Integer.parseInt(str[3]) < 0 || Integer.parseInt(str[3]) > 100) &&
                        (Integer.parseInt(str[4]) < 0 || Integer.parseInt(str[4]) > 100)) {
                    System.out.println("wrong format");
                } else {
                    studentAll.addStudent(str[0], str[1]);
                    course course = allCourse.findCourse(str[2]);
                    if (course == null) {
                        System.out.println(str[2] + " " + "does not exist");
                    } else {
                        if ((course.form == 1 && (str.length != 5)) || (course.form == 2 && (str.length != 4))) {
                            System.out.println(str[0] + " " + str[1] + " : access mode mismatch");
                        } else {
                            int q = cj.findScore(str[0], str[1], str[2]);
                            if (q == 0) {
                                if (course.form == 1) {
                                    cj.compulsory(str[0], str[1], course, Integer.parseInt(str[3]),
                                            Integer.parseInt(str[4]));
                                    studentAll.ifHasScore();
                                } else if (course.form == 2) {
                                    cj.elective(str[0], str[1], course, Integer.parseInt(str[3]));
                                    studentAll.ifHasScore();
                                }
                            }
                        }
                    }
                }
            } else {
                if (str.length == 2 || str.length == 3) {
                    if (str[0].length() > 10) {
                        System.out.println("wrong format");
                    } else {
                        if (str.length == 3) {
                            if (str[1].equals("必修") && str[2].equals("考察")) {
                                System.out.println(str[0] + " : course type & access mode mismatch");
                            } else if ((str[1].equals("必修") && str[2].equals("考试")) ||
                                    (str[1].equals("选修") && (str[2].equals("考试") || (str[2].equals("考察"))))) {
                                course ke = allCourse.findCourse(str[0]);
                                if (ke == null) {
                                    allCourse.addCourse(str[0], str[1], str[2]);
                                }
                            } else {
                                System.out.println("wrong format");
                            }
                        } else if (str.length == 2) {
                            if (str[1].equals("选修")) {
                                System.out.println("wrong format");
                            } else if (str[1].equals("必修")) {
                                course ke = allCourse.findCourse(str[0]);
                                if (ke == null) {
                                    allCourse.addCourse(str[0], str[1], "考试");
                                }
                            } else if (str[1].equals("实验")) {
    
                            } else {
                                System.out.println("wrong format");
                            }
                        }
                    }
                } else {
                    System.out.println("wrong format");
                }
            }
            s = input.nextLine();
            str = s.split(" ");
        }
        studentAll.studentSort(cj);
        allCourse.sort(cj);
        studentAll.classScoreSelection();
    }
    
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

class Student {
    
    protected String num;
    
    protected String name;
    
    protected int flag = 0;
    
    protected int score;
    
    public Student() {
    
    }
    
    public Student(String num, String name) {
        this.num = num;
        this.name = name;
    }
}

class StudentAll {
    
    protected Student[] stu = new Student[20];
    
    protected int sum = 0;
    
    public void addStudent(String num, String name) {
        int i = 0;
        if (sum == 0) {
            stu[0] = new Student(num, name);
            sum++;
        } else {
            for (i = 0; i < sum; i++) {
                if (num.equals(stu[i].num) && name.equals(stu[i].name)) {
                    break;
                }
            }
            if (i == sum) {
                stu[sum++] = new Student(num, name);
            }
        }
    }
    
    public void ifHasScore() {
        stu[sum - 1].flag = 1;
    }
    
    public void studentSort(Scores cj) {
        for (int i = 0; i < sum - 1; i++) {
            for (int j = i + 1; j < sum; j++) {
                if ((stu[i].num).compareTo(stu[j].num) > 0) {
                    Student t = stu[i];
                    stu[i] = stu[j];
                    stu[j] = t;
                }
            }
        }
        cj.studentScores(stu, sum);
    }
    
    public void classScoreSelection() {
        ClassSelection classSelection = new ClassSelection();
        classSelection.selection(stu, sum);
        classSelection.classScore();
    }
}

class Class {
    
    protected String classNumber;
    
    protected Student[] stu;
    
    protected int num;
    
    public Class(String classNumber, Student[] stu, int a) {
        this.classNumber = classNumber;
        this.stu = stu;
        this.num = a;
    }
    
    public int isClassScore(Class classes) {
        for (int i = 0; i < classes.num; i++) {
            if (classes.stu[i].flag != 0) {
                return 1;
            }
        }
        return 0;
    }
    
    public int classesScore(Class classes) {
        int m = 0;
        for (int i = 0; i < classes.num; i++) {
            if (classes.stu[i].score != 0) {
                m = m + classes.stu[i].score;
            }
        }
        return m / classes.num;
    }
}

class ClassSelection {
    
    Class[] classes = new Class[20];
    
    String[] strings = new String[20];
    
    Student[][] students = new Student[20][20];
    
    int[] num = new int[20];
    
    int c = 0;
    
    public void selection(Student[] stu, int a) {
        for (int i = 0; i < a; i++) {
            int j;
            String str = stu[i].num.substring(0, 6);
            if (c == 0) {
                strings[c] = str;
                students[c][num[c]] = stu[i];
                num[c] = num[c] + 1;
                c++;
            } else {
                for (j = 0; j < c; j++) {
                    if (str.equals(strings[j])) {
                        students[j][num[j]] = stu[i];
                        num[j] = num[j] + 1;
                        break;
                    }
                }
                if (j == c) {
                    strings[c] = str;
                    students[c][num[c]] = stu[i];
                    num[c] = num[c] + 1;
                    c++;
                }
            }
        }
        for (int i = 0; i < c; i++) {
            classes[i] = new Class(strings[i], students[i], num[i]);
        }
    }
    
    public void classScore() {
        for (int i = 0; i < c; i++) {
            if (classes[i].isClassScore(classes[i]) == 0) {
                System.out.println(classes[i].classNumber + " " + "has no grades yet");
            } else {
                System.out.println(classes[i].classNumber + " " + classes[i].classesScore(classes[i]));
            }
        }
    }
}

class course {
    
    protected String courseName;
    
    protected int form;
    
    protected int examForm;
    
    protected int flag = 0;
    
    public course() {
    
    }
    
    public course(String name, int form, int examForm) {
        this.courseName = name;
        this.form = form;
        this.examForm = examForm;
    }
}

class allCourse {
    
    protected course[] courses = new course[20];
    
    protected int num = 0;
    
    public void addCourse(String name, String form, String examForm) {
        int x = 0;
        int f = 0;
        if (form.equals("必修")) {
            x = 1;
        } else if (form.equals("选修")) {
            x = 2;
        }
        if (examForm.equals("考试")) {
            f = 1;
        } else if (examForm.equals("考察")) {
            f = 2;
        }
        courses[num] = new course(name, x, f);
        num++;
    }
    
    public course findCourse(String name) {
        for (int i = 0; i < num; i++) {
            if (name.equals(courses[i].courseName)) {
                return courses[i];
            }
        }
        return null;
    }
    
    public void sort(Scores cj) {
        Collator collator = Collator.getInstance(Locale.CHINA);
        for (int i = 0; i < num - 1; i++) {
            for (int j = i + 1; j < num; j++) {
                if (collator.compare(courses[i].courseName, courses[j].courseName) > 0) {
                    course t = courses[i];
                    courses[i] = courses[j];
                    courses[j] = t;
                }
            }
        }
        cj.score(courses, num);
    }
}

class Select {
    
    protected Student stu;
    
    protected course course;
    
    protected int scores;
    
    protected int daily;
    
    protected int exam;
    
    public Select(Student stu, course course, int daily, int exam, int scores) {
        this.stu = stu;
        this.course = course;
        this.daily = daily;
        this.exam = exam;
        this.scores = scores;
    }
    
    public Select(Student stu, course course, int[] scores) {
        this.stu = stu;
        this.course = course;
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        this.scores = sum / scores.length;
    }
    
    public void courseScore(int flag) {
        course.flag = flag;
    }
    
    public void studentScore(int flag) {
        stu.flag = flag;
    }
}

class Scores {
    
    protected Select[] selects = new Select[20];
    
    protected int num = 0;
    
    public void compulsory(String sum, String name, course course, int x, int y) {
        double z = x * 0.3 + y * 0.7;
        int t = (int) (z);
        Student stu = new Student(sum, name);
        selects[num] = new Select(stu, course, x, y, t);
        selects[num].courseScore(1);
        selects[num].studentScore(1);
        num++;
    }
    
    public void elective(String sum, String name, course course, int x) {
        Student stu = new Student(sum, name);
        selects[num] = new Select(stu, course, 0, x, x);
        selects[num].courseScore(1);
        selects[num].studentScore(1);
        num++;
    }
    
    
    public void studentScores(Student[] stu, int a) {
        int[] scj = new int[20];
        int[] c = new int[20];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < num; j++) {
                if ((stu[i].num.equals(selects[j].stu.num)) && (stu[i].name.equals(selects[j].stu.name))) {
                    scj[i] = scj[i] + selects[j].scores;
                    c[i]++;
                }
            }
        }
        for (int i = 0; i < a; i++) {
            int t = scj[i] / c[i];
            stu[i].score = t;
            if (stu[i].flag == 0) {
                System.out.println(stu[i].num + " " + stu[i].name + " did not take any exams");
            } else {
                System.out.println(stu[i].num + " " + stu[i].name + " " + t);
            }
        }
    }
    
    public void score(course[] courses, int a) {
        int[] dailyScore = new int[a];
        int[] examScore = new int[a];
        int[] kcj = new int[a];
        int[] c = new int[20];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < num; j++) {
                if (courses[i].courseName.equals(selects[j].course.courseName)) {
                    if (courses[i].form == 1) {
                        dailyScore[i] = dailyScore[i] + selects[j].daily;
                    }
                    examScore[i] = examScore[i] + selects[j].exam;
                    kcj[i] = kcj[i] + selects[j].scores;
                    c[i]++;
                }
            }
        }
        for (int i = 0; i < a; i++) {
            if (courses[i].flag == 0) {
                System.out.println(courses[i].courseName + " has no grades yet");
            } else if (courses[i].flag == 1) {
                if (courses[i].form == 1) {
                    System.out.println(courses[i].courseName + " " + (int) (dailyScore[i] / (1.0 * c[i])) + " " +
                            (examScore[i] / (c[i])) + " " + (kcj[i] / (c[i])));
                } else if (courses[i].form == 2) {
                    System.out.println(courses[i].courseName + " " + (int) (examScore[i] / (c[i] * 1.0)) + " " +
                            (kcj[i] / (c[i])));
                } else if (courses[i].form == 3) {
    
                    System.out.println();
                }
            }
        }
    }
    
    public int findScore(String str1, String str2, String str3) {
        for (int i = 0; i < num; i++) {
            if (str1.equals(selects[i].stu.num) && str2.equals(selects[i].stu.name) &&
                    str3.equals(selects[i].course.courseName)) {
                return 1;
            }
        }
        return 0;
    }
}