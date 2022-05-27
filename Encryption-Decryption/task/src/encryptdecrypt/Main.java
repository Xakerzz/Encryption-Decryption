package encryptdecrypt;

import java.io.*;

public class Main {
    static String in = "";
    static String out = "";
    static String operation = "enc";
    static String algorithm = "shift";
    static char[] arrayChar;
    static int key = 0;
    static boolean isFile = false;

    public static boolean isIsFile() {
        return isFile;
    }

    public static void setIsFile(boolean isFile) {
        Main.isFile = isFile;
    }

    public static void main(String[] args) {
        start(args);

    }

    public static void start(String[] args) {
        getArguments(args);
        doOperation(operation);
        printResult(arrayChar);
    }

    public static void getArguments(String[] args) {
        String tempData = "";
        for (int i = 0; i < args.length; i += 2) {
            if ("-mode".equals(args[i])) {
                operation = args[i + 1];
            } else if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            } else if ("-data".equals(args[i])) {
                tempData = args[i + 1];
            } else if ("-in".equals(args[i])) {
                in = args[i + 1];
            } else if ("-out".equals(args[i])) {
                out = args[i + 1];
            } else if ("-alg".equals(args[i])) {
                algorithm = args[i + 1];
            }
        }

        if (!"".equals(tempData)) {
            arrayChar = tempData.toCharArray();
        } else {
            readFile(in);
        }
    }

    public static void readFile(String in) {
        File pathToFile = new File(in);
        String temp = "";
        try (FileReader fileReader = new FileReader(pathToFile)) {
            int c;
            while ((c = fileReader.read()) != -1) {
                temp += (char) c;
            }

            arrayChar = temp.trim().toCharArray();
            setIsFile(true);
        } catch (FileNotFoundException e) {
            System.out.println("Error - the file is not found!");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static void doOperation(String operation) {

        switch (operation) {
            case "enc":
                doEnc(arrayChar);
                break;
            case "dec":
                doDec(arrayChar);
                break;
        }
    }

    public static void doEnc(char[] arrayChar) {

        for (int i = 0; i < arrayChar.length; i++) {
            if ("shift".equals(algorithm)) {
                if (arrayChar[i] >= 'a' && arrayChar[i] <= 'z') {
                    arrayChar[i] = (char) (((arrayChar[i] - 'a' + key) % 26) + 'a');
                } else if (arrayChar[i] >= 'A' && arrayChar[i] <= 'Z') {
                    arrayChar[i] = (char) (((arrayChar[i] - 'A' + key) % 26) + 'A');
                }
            } else if ("unicode".equals(algorithm)){
                arrayChar[i] += key;
                if (arrayChar[i] < 123 && arrayChar[i] > 96) {
                    if (arrayChar[i] > 122) {
                        arrayChar[i] -= 123;
                        arrayChar[i] += 97;
                    }
                }
            }
        }
    }

    public static void doDec(char[] arrayChar) {

        for (int i = 0; i < arrayChar.length; i++) {
            if ("shift".equals(algorithm)) {
                if (arrayChar[i] >= 'a' && arrayChar[i] <= 'z') {
                    arrayChar[i] = (char) ('z' - (('z' - arrayChar[i] + key) % 26));
                } else if (arrayChar[i] >= 'A' && arrayChar[i] <= 'Z'){
                    arrayChar[i] = (char) ('Z' -(('Z' - arrayChar[i] + key) % 26));
                }
            } else if ("unicode".equals(algorithm)) {
                arrayChar[i] -= key;
                if (arrayChar[i] < 123 && arrayChar[i] > 96) {
                    if (arrayChar[i] < 97) {
                        arrayChar[i] += 123 + key;
                        arrayChar[i] -= 96;
                    }
                }
            }

        }
    }


    public static void printResult(char[] arrayChar) {
        if (!isIsFile()) {
            System.out.println(arrayChar);
        } else {
            File pathToOutFile = new File(out);
            try (FileWriter fileWriter = new FileWriter(pathToOutFile, false)) {
                fileWriter.write(arrayChar);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }
}