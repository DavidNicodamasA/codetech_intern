import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "sample.txt";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nFile Operations Menu:");
            System.out.println("1. Write to File");
            System.out.println("2. Read from File");
            System.out.println("3. Modify a Line in File");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    writeFile(scanner);
                    break;
                case 2:
                    readFile();
                    break;
                case 3:
                    modifyFile(scanner);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void writeFile(Scanner scanner) {
        System.out.println("Enter text to write to the file:");
        String content = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(content);
            writer.newLine();
            System.out.println("Content written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("File Contents:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void modifyFile(Scanner scanner) {
        System.out.print("Enter the line number to modify: ");
        int lineNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new text:");
        String newText = scanner.nextLine();
        
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }
        
        if (lineNumber < 1 || lineNumber > lines.size()) {
            System.out.println("Invalid line number.");
            return;
        }
        
        lines.set(lineNumber - 1, newText);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("File updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
