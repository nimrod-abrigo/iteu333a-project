package finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinalProject {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        List variables = new ArrayList();

        //reads the file "input.txt"
        PrintWriter writer = new PrintWriter("Test.java", "UTF-8");

        writer.println("public class Test {");
        writer.println("public static void main(String[] args){");
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            if (str.contains("//")) {
                continue;
            }
            if (str.contains(";")) {
                if (str.charAt(str.length() - 1) == ';') {
                    if (regexChecker("iprint[(][\"][a-zA-Z0-9 -+*/><{}:,']*[\"][)][;]", str)) {
                        str = str.replace("iprint", "System.out.println");
                        writer.println(str);
                    } else if (regexChecker("int [a-zA-Z]*[0-9]* [=] [0-9]{1,8}[;]", str)) {
                        writer.println(str);
                        variables.add(getVariable(str));
                    } else if (regexChecker("String [a-zA-Z]{1,}[0-9]* [=] [\"][a-zA-Z0-9 ]*[\"][;]", str)) {
                        writer.println(str);
                        variables.add(getVariable(str));
                    } else if (regexChecker("float [a-zA-Z]{1,}[0-9]* [=] [+-]\\d{1,8}[.]\\d{1,8}[;]", str)) {
                        writer.println(str);
                        variables.add(getVariable(str));
                    } else if (str.contains("printVar[(][a-zA-Z0-9 -+*/><{}:,']*[)][;]")) {
                        str = str.replace("printVar", "System.out.println");
                        writer.println(str);
                    } else {
                        writer.println("//May syntax error");
                        writer.println(str);
                    }
                }
            } else {
                writer.println("//Walang Semi Colon");
                writer.println(str);
            }
            //replaces iprint to System.out.println
        }
        writer.println("}");
        writer.println("}");
        writer.close();
        /*List cmdAndArgs = Arrays.asList("cmd", "/c", "run.bat");
        File dir = new File(System.getProperty("user.dir"));

        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        Process p = pb.start();*/
    }

    public static boolean regexChecker(String theRegex, String str2Check) {
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher(str2Check);

        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                return true;
            }
        }
        return false;
    }

    public static String getVariable(String input) {
        String[] parts = input.split(" ");
        return parts[1];
    }
    
    public static String getVarFromPrint(String input){
        return "hahaha";
    }

}
