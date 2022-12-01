import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Questions:
        // Est ce que les registres sont signés ou non signés ?

        // Read file
        ArrayList<String> codeLines_withComments = readFile("first_file.txt");
        ArrayList<String> codeLines = removeComments(codeLines_withComments);

        // Init Stack
        Stack stack = new Stack();

        // Init Memory for variable
        Memory memory = new Memory();



        // Init ALU
        ALU alu = new ALU();
        alu.memory = memory;
        alu.stack = stack;
        alu.labelToCodeLine = new HashMap<>();

        // Program Counter
        int PC = 0;

        // Start the execution
         startExecution(codeLines, alu, PC, memory);

        // TESTING
        // TEST_memory();
        // TEST_twosComplement();
        // TEST_binaryAddition();
        // TEST_binarySubtraction();
    }


    public static ArrayList<String> readFile(String fileName) throws FileNotFoundException {

        // Stores each line in an ArrayList "lines"
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while(scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        return lines;
    }

    public static ArrayList<String> removeComments(ArrayList<String> codeLines_withComments){

        // Modify the ArrayList argument codeLines_withComments by removing each line beginning with '!'
        int number_of_lines = codeLines_withComments.size();
        ArrayList<String> codeLines = new ArrayList<>(number_of_lines);
        for (String line : codeLines_withComments) {
            if (line.charAt(0) != '!')
                codeLines.add(line);
        }

        return codeLines;
    }
    public static void printVaraible(Memory memory) {
        // Parcour le dic
        // Regarde aux addresses
        // bin to dec
        // affiche les values
    }

    public static void startExecution(ArrayList<String> codeLines, ALU alu, int PC, Memory memory) {
        boolean lines_remaining = true;
        boolean DATA_SECTION = false;
        boolean CODE_SECTION = false;

        boolean prompt = true;

        while(lines_remaining) {
            String line = codeLines.get(PC);

            if (Objects.equals(line, "#CODE")) {
                DATA_SECTION = false;
                CODE_SECTION = true;
                System.out.println("Data section finished");
                System.out.println("Starting code section..");
            }

            if (DATA_SECTION) {
                memory.writeVariableFromCodeLine(line);
            }

            if (CODE_SECTION) {
                PC = code_section(line, alu, PC);
                if (prompt) {
                    alu.t0.print();
                    alu.t1.print();
                    alu.t2.print();
                    alu.t3.print();
                }
            }

            if (Objects.equals(line, "#DATA")) {
                System.out.println("Starting data section..");
                DATA_SECTION = true;
            }

            // Stop condition
            if (Objects.equals(line, "HLT")) {
                System.out.println("Code section finished");
                lines_remaining = false;
            }
            PC++;
        }
    }

    //TODO
    public static void printVariable() {

    }




    public static int code_section(String line, ALU alu, int PC) {
        String[] lineElements = line.split(" ");

        // Check for label
        if (lineElements.length == 1) {

            // Label example : "LOOP:"
            int insctructionLength = lineElements[0].length();

            // Remove ":" at the end
            String LABEL = lineElements[0].substring(0, insctructionLength-1);

            // Store in Map
            alu.labelToCodeLine.put(LABEL, PC);
        }

        String instruction = lineElements[0];

        switch (instruction) {
            case "LDA":
                if (lineElements.length == 3) {
                    Instructions.LDA(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "STR":
                if (lineElements.length == 3) {
                    Instructions.STR(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "PUSH":
                if (lineElements.length == 2) {
                    Instructions.PUSH(lineElements[1], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "POP":
                if (lineElements.length == 2) {
                    Instructions.POP(lineElements[1], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "AND":
                if (lineElements.length == 3) {
                    Instructions.AND(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "OR":
                if (lineElements.length == 3) {
                    Instructions.OR(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "NOT":
                if (lineElements.length == 2) {
                    Instructions.NOT(lineElements[1], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "ADD":
                if (lineElements.length == 3) {
                    Instructions.ADD(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "SUB":
                if (lineElements.length == 3) {
                    Instructions.SUB(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "DIV":
                if (lineElements.length == 3) {
                    Instructions.DIV(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "MUL":
                if (lineElements.length == 3) {
                    Instructions.MUL(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "MOD":
                if (lineElements.length == 3) {
                    Instructions.MOD(lineElements[1], lineElements[2], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "INC":
                if (lineElements.length == 2) {
                    Instructions.INC(lineElements[1], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "DEC":
                if (lineElements.length == 2) {
                    Instructions.DEC(lineElements[1], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "BEQ":
                if (lineElements.length == 4) {
                    return Instructions.BEQ(lineElements[1], lineElements[2], lineElements[3], PC, alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "BNE":
                if (lineElements.length == 4) {
                    return Instructions.BNE(lineElements[1], lineElements[2], lineElements[3], PC, alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "BBG":
                if (lineElements.length == 4) {
                    return Instructions.BBG(lineElements[1], lineElements[2], lineElements[3], PC, alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "BSM":
                if (lineElements.length == 4) {
                    return Instructions.BSM(lineElements[1], lineElements[2], lineElements[3], PC, alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;

            case "JMP":
                if (lineElements.length == 2) {
                    return Instructions.JMP(lineElements[1], alu);
                }
                else {
                    System.out.println("Error: invalid number of argument:");
                    System.out.println("In line: " + line);
                }
                break;
        }
        return PC;
    }

    // TEST functions ------------------------------------
    public  static void TEST_memory() {
        Memory memory = new Memory();
        String codeLine = "A 10";
        String codeLine2 = "B 127";
        memory.writeVariableFromCodeLine(codeLine);
        memory.writeVariableFromCodeLine(codeLine2);
        memory.print(100);
    }
    public static void TEST_twosComplement() {
        int numberToTest = 127;

        Register reg1 = new Register("reg1");
        reg1.write(Tools.convertDecToBin32(numberToTest));
        String binaryString = reg1.read();
        String complement = Tools.twosComplement(binaryString);
        System.out.println(binaryString);
        System.out.println(complement);
    }
    public static void TEST_binaryAddition() {
        System.out.println("TEST_binaryAddition");
        // number1 + number2
        // Change number to test
        int number1 = 1;
        int number2 = 127;

        Register reg1 = new Register("reg1");
        reg1.write(Tools.convertDecToBin32(number1));
        Register reg2 = new Register("reg2");
        reg1.write(Tools.convertDecToBin32(number2));

        reg1.print();
        reg2.print();

        System.out.println();

        String res = Tools.binaryAddition32bit(reg1.read(), reg2.read());
        System.out.println("res: " + res);
    }

    public static void TEST_binarySubtraction() {
        System.out.println("TEST_binarySubtraction");
        // number1 - number2
        // Change numbers to test
        int number1 = 64;
        int number2 = 128;

        Register reg1 = new Register("reg1");
        reg1.write(Tools.convertDecToBin32(number1));
        Register reg2 = new Register("reg2");
        reg1.write(Tools.convertDecToBin32(number2));

        reg1.print();
        reg2.print();

        System.out.println();

        String res = Tools.binarySubtraction32bit(reg1.read(), reg2.read());
        System.out.println(number1 + " - " + number2 + " = " + Tools.convertBin32ToDec(res) + String.format("(expected %d)", number1 - number2));
        System.out.println("res: " + res);
    }
}
