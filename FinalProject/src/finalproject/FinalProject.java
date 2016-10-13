package finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FinalProject {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));

        //reads the file "input.txt"
        PrintWriter writer = new PrintWriter("Test.java", "UTF-8");

        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            if (str.contains("//")) {
                continue;
            }else if (str.contains("public")) {
                writer.println(str);
            }else if (str.contains("int")) {
                writer.println(str);
            }else if (str.contains("String")) {
                writer.println(str);
            }else if (str.contains("iprint")) {
                str = str.replace("iprint", "System.out.println");
                writer.println(str);
            }else if (str.contains("{")) {
                writer.println(str);
            }else if (str.contains("}")) {
                writer.println(str);
            } else{
                System.out.println(str);
                break;
            }
            
            //replaces iprint to System.out.println
           
            
        }

        writer.close();
        /*List cmdAndArgs = Arrays.asList("cmd", "/c", "run.bat");
        File dir = new File(System.getProperty("user.dir"));

        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        Process p = pb.start();*/
    }

    

}
