import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter latitude");
        double latitude = scanner.nextDouble();

        System.out.println("Enter longitude");
        double longitude = scanner.nextDouble();

        System.out.println("Enter days limit, from 1 to 7");
        int limit = scanner.nextInt();

        Weather.getData(latitude, longitude, limit);
    }
}