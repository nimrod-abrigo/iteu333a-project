package finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinalProject {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        Set<String> variables = new HashSet<String>();
        Set<String> strVar = new HashSet<String>();
        boolean error = false;
        String[] parts;

        //reads the file "input.txt"
        PrintWriter writer = new PrintWriter("Test.java", "UTF-8");

        writer.println("public class Test {");
        writer.println("public static void main(String[] args){");
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            if (str.contains("//")) {
                continue;
            } else if (str.length() == 0) {
                writer.println();
                continue;
            
            }else if (str.contains(";")) {
                if (str.charAt(str.length() - 1) == ';') {
                    if (regexChecker("iprint[(][\"][a-zA-Z0-9 -+*/><{}:,']*[\"][)][;]", str)) {
                        str = str.replace("iprint", "System.out.println");
                        writer.println(str);
                    } else if (regexChecker("iprint[(][a-zA-Z]*[0-9_]*[)][;]", str)) {
                        if (getVarFromPrint(str) != "wala") {
                            String variable = getVarFromPrint(str);
                            if (variables.contains(variable)) {
                                str = str.replace("iprint", "System.out.println");
                                writer.println(str);
                            } else {
                                writer.println(str + " //Variable not declared");
                            }
                        }
                    } else if (regexChecker("getHaba[(][\"][a-zA-Z0-9 -+*/><{}:,']*[\"][)][;]", str)) {
                        if (getHabaString(str) != "wala") {
                            writer.println("String programInput = " + getHabaString(str) + ";");
                            str = str.replace("getHaba", "System.out.println");
                            str = str.replace(getHabaString(str), "programInput.length()");
                            writer.println(str);
                        } else {
                            writer.println(str + " //Syntax Error");
                        }
                    } else if (regexChecker("getHaba[(][a-zA-Z]*[0-9_]*[)][;]", str)) {
                        if (getVarFromPrint(str) != "wala") {
                            String variable = getVarFromPrint(str);
                            if (variables.contains(variable)) {
                                str = str.replace("getHaba", "System.out.println");
                                str = str.replace(variable, variable + ".length()");
                                writer.println(str);
                            } else {
                                writer.println(str + " //Variable not declared");
                            }
                        }
                    } else if (regexChecker("int [a-zA-Z]{1,}[0-9_]* [=] [-]*[0-9]{1,8}[;]", str)) {
                        writer.println(str);
                        variables.add(getVariable(str));
                    } else if (regexChecker("String [a-zA-Z]{1,}[0-9_]* [=] [\"][a-zA-Z0-9 ]*[\"][;]", str)) {
                        writer.println(str);
                        variables.add(getVariable(str));
                        strVar.add(getVariable(str));
                    } else if (regexChecker("double [a-zA-Z]{1,}[0-9_]* [=] [-]*\\d{1,8}[.]\\d{1,8}[;]", str)) {
                        writer.println(str);
                        variables.add(getVariable(str));
                    }
                    else if (regexChecker("compute[(](([a-zA-Z]+[0-9_]*)*\\s?[+\\-/*%]\\s?([a-zA-Z]+[0-9_]*)+)*[)][;]", str)) {
                        //wala pang variable declaration
                        str = str.replace("compute", "System.out.println");
                        parts = str.split(" ");
                        boolean sira = false;
                        for (int i = 0; i < parts.length - 1; i++) {
                            parts[i] = parts[i].replace("System.out.println(", "");
                            parts[i] = parts[i].replace(")", "");
                            if (parts[i] == "+" || parts[i] == "-" || parts[i] == "*" || parts[i] == "/") {
                                continue;
                            } else if (regexChecker("[a-zA-Z]{1,}[0-9_]*", parts[i])) {
                                continue;
                            } else {
                                sira = true;
                                break;
                            }
                        }
                        if (sira) {
                            writer.println(str);
                        } else {
                            writer.println(str + "//maling formula");
                        }

                        }else if (regexChecker("[a-zA-Z]{1,}[0-9_a-zA-Z]*[.]dugtong[(][a-zA-Z]{1,}[0-9_a-zA-Z]*[)][;]", str)) {
                        if (getVarFromPrint(str) != "wala") {
                            String variable1 = getVarConcat(str);
                            String variable2 = getVarFromPrint(str);
                            if (variables.contains(variable1) || variables.contains(variable2)) {
                                writer.println("string programdugtong = " + variable1 + ".concat(" + variable2 + ");");
                                writer.println("System.out.println(programdugtong)");
                                writer.println(str);
                            }
                    else {
                        writer.println(str + " //May syntax error");
                    }
                        }
                    }
                }
            else {
                writer.println(str + " //Walang Semi Colon");
            }
                }
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

    public static String getVarFromPrint(String input) {
        Pattern checkRegex = Pattern.compile("[(][a-zA-Z]{1,}[0-9_]*[)]");
        Matcher regexMatcher = checkRegex.matcher(input);

        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                String var = regexMatcher.group();
                var = var.replace("(", "");
                var = var.replace(")", "");
                return var;
            }
        }
        return "wala";
    }

    public static String getHabaString(String input) {
        Pattern checkRegex = Pattern.compile("[(][\"][a-zA-Z]{1,}[0-9_]*[\"][)]");
        Matcher regexMatcher = checkRegex.matcher(input);

        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                String var = regexMatcher.group();
                var = var.replace("(", "");
                var = var.replace(")", "");
                return var;
            }
        }
        return "wala";
    }
    public static String getVarConcat(String input) {
        Pattern checkRegex = Pattern.compile("[a-zA-Z]{1,}[0-9_a-zA-Z]*[.]");
        Matcher regexMatcher = checkRegex.matcher(input);

        while (regexMatcher.find()) {
            if (regexMatcher.group().length() != 0) {
                String var = regexMatcher.group();
                var = var.replace(".", "");
                return var;
            }
        }
        return "wala";
    }
}
