package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;


public class ImportQandA {


    public String question;
    public String correctAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;
    public String wrongAnswer3;


    public ImportQandA(String q, String correctAnswer, String wrongAnswer1, String wrongAnswer2,
                        String wrongAnswer3) {

                this.question = q;
                this.correctAnswer = correctAnswer;
                this.wrongAnswer1 = wrongAnswer1;
                this.wrongAnswer2 = wrongAnswer2;
                this.wrongAnswer3 = wrongAnswer3;
    }

    /**Fill the stack with questions and answers.
     *It reads the txt file line by line( in a sample like question, correct answer, wrong answer, wrong answer, wrong answer) and push it to the stack after every fifth line.
     * @param stack Stack data structure, in order to use pop, peek, push methods.
     * @param data  File object, it contains the file`s path.
     */
    public static void filler(Stack<ImportQandA> stack, File data) {
        String question, answer, wrongOne, wrongTwo, wrongThree;
        question = answer = wrongOne = wrongTwo = wrongThree = null;
        int i = 0;
        try {
            Scanner dataReader = new Scanner(data);
            while (dataReader.hasNextLine()) {
                if (i == 0) {
                    question = dataReader.nextLine();
                    i++;
                } else if (i == 1) {
                    answer = dataReader.nextLine();
                    i++;
                } else if (i == 2) {
                    wrongOne = dataReader.nextLine();
                    i++;
                } else if (i == 3) {
                    wrongTwo = dataReader.nextLine();
                    i++;
                } else if (i == 4) {
                    wrongThree = dataReader.nextLine();
                    i++;
                } else if (i == 5) {
                    stack.push(new ImportQandA(question, answer, wrongOne, wrongTwo, wrongThree));
                    i = 0;
                }


            }
        } catch (FileNotFoundException e) {
            System.out.println("There is no available file");
        }
    }


}

