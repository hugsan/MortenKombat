package core;

import java.io.*;
import java.util.*;



public class ImportQandA {



        public String number;
        public String question;
        public String correctAnswer;
        public String wrongAnswer1;
        public String wrongAnswer2;
        public String wrongAnswer3;


        File data = new File("assets\\QnAData.txt");

        public ImportQandA(int numOfQ) throws FileNotFoundException{


            Scanner dataReader = new Scanner(data);



            while (dataReader.hasNextLine()) {


                number = "p00" + numOfQ;
                if (number.equals(dataReader.next())) {

                    dataReader.nextLine();
                    this.question = dataReader.nextLine();
                    System.out.println(question);
                    this.correctAnswer = dataReader.nextLine();
                    System.out.println(correctAnswer);
                    this.wrongAnswer1 = dataReader.nextLine();
                    System.out.println(wrongAnswer1);
                    this.wrongAnswer2 = dataReader.nextLine();
                    System.out.println(wrongAnswer2);
                    this.wrongAnswer3 = dataReader.nextLine();
                    System.out.println(wrongAnswer3);

                }

            }

        }
}

