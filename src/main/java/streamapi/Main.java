package streamapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/** Starter for the stream api task. */
public class Main {
    /**
     * And go.
     *
     * @param args command line parameters, not used
     */
    public static void main(String... args) {

        // Task I: Students
        System.out.println(
                students(
                        List.of(
                                new Student("A", 30, Enrollment.IFM),
                                new Student("B", 45, Enrollment.IFM),
                                new Student("C", 60, Enrollment.ELT),
                                new Student("D", 45, Enrollment.ARCH),
                                new Student("E", 80, Enrollment.IFM))));

        // Task II: Set of ECTS of all IFM students
        System.out.println(
                ifmCps(
                        List.of(
                                new Student("A", 35, Enrollment.IFM),
                                new Student("B", 35, Enrollment.IFM),
                                new Student("C", 60, Enrollment.ELT),
                                new Student("D", 45, Enrollment.ARCH),
                                new Student("E", 80, Enrollment.IFM))));

        // Task III: Random
        System.out.println(random());

        // Task IV+V: Resources
        System.out.println(resources("file.txt"));
    }

    /**
     * Task I: Students.
     *
     * <p>Calculate the total credits earned by all students.
     *
     * @param studentList List of students
     * @return Sum of credit points of all students
     */
    public static Integer students(List<Student> studentList) {
        return (int)studentList.stream()
            .map(Student::cps)
            .reduce(0,Integer::sum);
    }

    /**
     * Task II: Set of ECTS of all IFM students.
     *
     * <p>Identify the different credit points of all IFM students.
     *
     * @param studentList List of students
     * @return Set of credit points of all IFM students
     */
    public static Set<Integer> ifmCps(List<Student> studentList) {
        return studentList.stream()
            .filter(Student::isIFM)
            .map(Student::cps)
            .collect(Collectors.toSet());
    }

    /**
     * Task III: Random.
     *
     * <p>Calculate ten random integers between 0 and 10.
     *
     * @return List of ten random integers (between 0 and 10)
     */
    public static List<Integer> random() {
        Random r = new Random();
        IntStream numberStream = r.ints(10, 0, 10);
        List<Integer> rl =
                numberStream.filter(Number -> Number % 2 == 0).boxed().collect(Collectors.toList());
        return rl;
    }

    /**
     * Task IV: Open resources.
     *
     * <p>Open the file specified by the {@code path} parameter. This file is located in the
     * resources folder of the project.
     *
     * @param path Name of the file to be accessed within the resource folder.
     * @return An open {@link InputStream} for the resource file
     */
    private static InputStream getResourceAsStream(String path) {
        InputStream stream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

        if (stream == null) {
            throw new IllegalArgumentException(path + " not found");
        }

        return stream;
    }

    /**
     * Task V: Read resources.
     *
     * <p>Read all lines from the resource file (specified by the {@code path} parameter). Merge all
     * lines that start with the letter "a" and are at least two characters long. The lines are to
     * be separated in the resulting string by a line-end character {@code "\n"}.
     *
     * @param path Name of the file to be accessed within the resource folder
     * @return String of all matching lines, separated by {@code "\n"}
     */
    public static String resources(String path) {

        try (InputStream stream = getResourceAsStream(path)) {
            return new BufferedReader(new InputStreamReader(stream))
                    .lines()
                    .filter(line -> line.startsWith("a") && line.length() >= 2)
                    .collect(Collectors.joining("\n"));

        } catch (IOException e) {
            System.err.println("Ouch, that didn't work: \n" + e.getMessage());
            return null;
        }
    }
}
