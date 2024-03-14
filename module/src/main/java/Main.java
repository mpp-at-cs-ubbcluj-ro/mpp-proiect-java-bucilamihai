import domain.Child;
import domain.Enrollment;

public class Main {
    public static void main(String[] args) {
        Child child = new Child(5123456789123L, "abc", 10);
        System.out.println(child);
        System.out.println(child.getId());
        Enrollment enrollment = new Enrollment(child, null);
        System.out.println(enrollment);

        System.out.println("Hello World!");
        System.out.println();
    }
}