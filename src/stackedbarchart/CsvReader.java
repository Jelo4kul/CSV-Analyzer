package stackedbarchart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jelo
 */
public class CsvReader {

    private int count;
    private final ArrayList<String> arr = new ArrayList<>();
    private static ArrayList[] myArr;
    public static ArrayList<String> selectedColumnArrayList = new ArrayList<>();
    public static ArrayList<String> selectedColumnArrayList2 = new ArrayList<>();
    public static String filePath;

    public void printAllColumns(String fileUrl, int colCount, ObservableList<ObservableList> data) {

        int myC=0;
        try {
            FileReader reader = new FileReader(fileUrl);
            BufferedReader bufReader = new BufferedReader(reader);

            initializeArray(fileUrl);

            String line;
            while ((line = bufReader.readLine()) != null) {
               // System.out.print((++myC)+").");
                splitLine(line);
            }

            reader.close();
            bufReader.close();

            fillTable(data, colCount);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Please select a file");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Please select a file");
            System.out.println("Input/Output Exception");
        }

    }

    public void fillTable(ObservableList<ObservableList> data, int amtOfCols) {
        data.clear();

        int totalNumberOfRows = myArr[0].size();

        for (int k = 0; k < totalNumberOfRows; k++) {
            //creation of a new observable list
            ObservableList<String> rows = FXCollections.observableArrayList();

            //iterate columns
            for (int i = 0; i < amtOfCols; i++) {
                //  System.out.println(myArr[i].get(k).toString());
                rows.add(myArr[i].get(k).toString());
            }

            data.add(rows);
        }
    }

    //this method works by adding all the elements in a column to an arraylist
    public void printSpecificColumn(int col) {

        for (int j = 0; j < myArr[col].size(); j++) {
            selectedColumnArrayList.add(myArr[col].get(j).toString());
        }

    }

    public void initializeArray(String fileUrl) {
        try {

            FileReader reader = new FileReader(fileUrl);
            BufferedReader bufReader = new BufferedReader(reader);

            String line;
            if ((line = bufReader.readLine()) != null) {
                int colCount = getColumnCount(line);

                myArr = new ArrayList[colCount];
                for (int i = 0; i < colCount; i++) {
                    myArr[i] = new ArrayList<>();
                }

            }

            reader.close();
            bufReader.close();

        } catch (Exception e) {

        }

    }

    public void splitLine(String s) {
        count = 0;

        boolean notInsideComma = true;
        int start = 0, end = 0;
        //creation of a new observable list
        ObservableList<String> rows = FXCollections.observableArrayList();

      
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ',' && notInsideComma) {
                //System.out.print(s.substring(start, i)+",");
                myArr[count].add(s.substring(start, i));
                count++;
                start = i + 1;
            } else if (s.charAt(i) == '"') {
                notInsideComma = !notInsideComma;
            }
        }
        myArr[count].add(s.substring(start));
        count++;
        //System.out.println("");
    }

    public int getColumnCount(String line) {
        count = 0;
        boolean notInsideComma = true;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',' && notInsideComma) {
                count++;
            } else if (line.charAt(i) == '"') {
                notInsideComma = !notInsideComma;
            }
        }
        count++;
        return count;
    }

    //this method counts columns given a fileUrl as the parameter
    //it is called from the StartTab class
    public int countColumns(String fileUrl) {

        int colCount = 0;
        try {

            FileReader reader = new FileReader(fileUrl);
            BufferedReader bufReader = new BufferedReader(reader);

            String line;
            if ((line = bufReader.readLine()) != null) {
                colCount = getColumnCount(line);
            }

            reader.close();
            bufReader.close();

        } catch (Exception e) {

        }
        return colCount;

    }

}
