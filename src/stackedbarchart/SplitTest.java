/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stackedbarchart;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Jelo
 */
public class SplitTest {

    public static void main(String[] args) {

        String s = "";
        String[] splitted = null;

        //Measure Regular Expression
        long startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            splitted = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        }
        long endTime = System.nanoTime();

        System.out.println("Took: " + (endTime - startTime));
        System.out.println(Arrays.toString(splitted));
        System.out.println(splitted.length);
        System.out.println("");

        ArrayList<String> sw = null;
        //Measure Custom Method
        startTime = System.nanoTime();
        // for (int i = 0; i < 3; i++) {
        sw = customSplitSpecific(s);
        //}
        endTime = System.nanoTime();

        System.out.println("Took: " + (endTime - startTime));
        System.out.println(sw);

    }

    public static ArrayList<String> customSplitSpecific(String s) {
        ArrayList<String> words = new ArrayList<String>();
        boolean notInsideComma = true;
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',' && notInsideComma) {
                words.add(s.substring(start, i));
                System.out.println(s.substring(start, i));
                start = i + 1;
            } else if (s.charAt(i) == '"') {
                notInsideComma = !notInsideComma;
            }
        }
        words.add(s.substring(start));
        System.out.println(s.substring(start));
        System.out.println(words.size());
        return words;

    }
}
