package stackedbarchart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Jelo
 */
public class StackedBarChartCreator {

    private final ArrayList<String> categoryNames = new ArrayList<>();
    private final double numbOfBars = 10;
    private final HashMap<String, Integer> map = new HashMap<>();
    private static StackPane barChartHolder;
    private int largestOccurrence;
    private final ArrayList<ArrayList<Integer>> seriesValue = new ArrayList<>();
    private final ArrayList<ArrayList<String>> seriesName = new ArrayList<>();
    private int largestStackedBars;
    private String key;
    Label labelHover;
    private final Glow glow = new Glow(.5);

    private void setupHover(XYChart.Series<String, Number> series) {
        for (final XYChart.Data<String, Number> dt : series.getData()) {
            final Node n = dt.getNode();
            StackPane l = (StackPane) n;
            Label lb = (Label) l.getChildren().get(0);

            n.setEffect(null);
            n.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                    lb.setText(dt.getExtraValue() + " : " + dt.getYValue());
                    l.toFront();
 
                    n.setScaleX(1.05);
                    n.setScaleY(1.05);
                    n.setEffect(glow);
                }
            });
            n.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    lb.setText("");

                    n.setScaleX(1);
                    n.setScaleY(1);
                    n.setEffect(null);
                }
            });
            n.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {

                }
            });
        }
    }

    public StackPane getBarChart() {

        barChartHolder = new StackPane();

        return barChartHolder;
    }

    public StackPane populateBarChart() {

        countDataInSelectedColumn(CsvReader.selectedColumnArrayList);

        largestOccurrence = getLargestOccurrence();

        obtainCategory();

        countLargestStackedBars();

        //defining the x axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(categoryNames));
        xAxis.setLabel("Range");

        //defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Frequency");

        //creating the barchart
        StackedBarChart<String, Number> bChart = new StackedBarChart<>(xAxis, yAxis);
        bChart.setTitle("Historic world population");

        fillSeriesWithData();

        for (int j = 0; j < largestStackedBars; j++) {
            //preparing the series
            XYChart.Series<String, Number> eachSeries = new XYChart.Series<>();
            eachSeries.setName("segment " + (j + 1));

            //adding data to the series
            for (int i = 0; i < categoryNames.size(); i++) {

                XYChart.Data data = new XYChart.Data<>(categoryNames.get(i), valueAt(j, i));
                data.setExtraValue(keyAt(j, i));

                Label lb = new Label();
                lb.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                lb.setStyle("-fx-background-color: lightblue");
                //lb.setTranslateX(-60);

                StackPane sp = new StackPane();
                sp.getChildren().add(lb);

                data.setNode(sp);
                eachSeries.getData().add(data);
            }

            bChart.getData().add(eachSeries);
            setupHover(eachSeries);
        }
        barChartHolder.getChildren().add(bChart);

        return barChartHolder;
    }

    public void obtainCategory() {

        double startOfCategory = 0;

        double staticCategoryRange = largestOccurrence / numbOfBars;
        double dynamicCategoryRange = largestOccurrence / numbOfBars;
        for (int i = 0; i < numbOfBars; i++) {
            String categoryName = Math.round(startOfCategory) + " - " + Math.round(dynamicCategoryRange);
            categoryNames.add(categoryName);

            startOfCategory = dynamicCategoryRange + 1;

            dynamicCategoryRange += staticCategoryRange;
        }
    }

    public int valueAt(int seriesNumber, int sValue) {

        return seriesValue.get(seriesNumber).get(sValue);
    }

    public String keyAt(int seriesNumber, int sValue) {

        return seriesName.get(seriesNumber).get(sValue);
    }

    public void putSeries(ArrayList<Integer> singleSerie) {
        seriesValue.add(singleSerie);
    }

    public void putSeriesName(ArrayList<String> singleSerie) {
        seriesName.add(singleSerie);
    }

    public void fillSeriesWithData() {

        HashMap<String, Integer> tempMap = new HashMap<>();
        tempMap = map;

        for (int j = 0; j < largestStackedBars; j++) {
            ArrayList<Integer> singleSerieValue = new ArrayList<>();
            ArrayList<String> singleSerieName = new ArrayList<>();

            for (int i = 0; i < categoryNames.size(); i++) {
                int serieValue = getSerieValue(i, tempMap);
                String serieKey = getSerieKey();

                singleSerieValue.add(serieValue);
                singleSerieName.add(serieKey);
            }
            putSeries(singleSerieValue);
            putSeriesName(singleSerieName);
        }

    }

    private Integer getSerieValue(int index, HashMap<String, Integer> tempMap) {
        int value = 0;
        String key = "";
        for (HashMap.Entry<String, Integer> e : tempMap.entrySet()) {
            if (isInRange(e.getValue(), index)) {
                value = e.getValue();
                setSerieKey(e.getKey());
                tempMap.remove(e.getKey(), value);
                break;
            }
        }
        return value;
    }

    private void setSerieKey(String key) {
        this.key = key;
    }

    private String getSerieKey() {
        return key;
    }

    private boolean isInRange(int value, int i) {
        String range = categoryNames.get(i);
        String arryRange[] = range.split(" - ");
        double fValue = Double.parseDouble(arryRange[0]);
        double sValue = Double.parseDouble(arryRange[1]);
        return value >= fValue && value <= sValue;
    }

    public void countDataInSelectedColumn(ArrayList<String> data) {

        for (String word : data) {
            Integer count = map.get(word);
            map.put(word, (count == null) ? 1 : count + 1);
        }

    }

    public int getLargestOccurrence() {
        Map.Entry<String, Integer> maxEntry = null;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getValue();
    }

    //[3, 4, 6, 6, 9, 30]
    private boolean isInNewRange(int previousValue, int currentValue) {
        int rangeOfPreviousValue = 0;
        int rangeOfCurrentValue = 0;
        for (int i = 0; i < categoryNames.size(); i++) {
            String range = categoryNames.get(i);
            String arryRange[] = range.split(" - ");
            int fValue = Integer.parseInt(arryRange[0]);
            int sValue = Integer.parseInt(arryRange[1]);
            for (int j = fValue; j <= sValue; j++) {
                if (previousValue == j) {
                    rangeOfPreviousValue = i;

                }
                if (currentValue == j) {
                    rangeOfCurrentValue = i;
                }
                if (rangeOfCurrentValue != 0 && rangeOfPreviousValue != 0) {
                    break;
                }
            }
            if (rangeOfCurrentValue != 0 && rangeOfPreviousValue != 0) {
                break;
            }
        }
        return rangeOfPreviousValue == rangeOfCurrentValue;

    }

    private int countLargestStackedBars() {
        int count = 0;
        int previousCount = 0;
        int largestStackB = 0;
        List<Integer> list = new ArrayList<>(map.values());
        Collections.sort(list);

        int previousValue = list.get(0);
        for (int currentValue : list) {

            if ((currentValue != previousValue) && isInNewRange(previousValue, currentValue)) {
                count = 1;
            } else {
                count++;
            }

            if (count > previousCount) {
                largestStackB = count;
            }
            previousValue = currentValue;
            previousCount = count;
        }

        largestStackedBars = largestStackB;
        return count;
    }

    /*public void countDataInSelectedColumn(ArrayList<String> data) {
    int count = 0;
    int mode = 0;
    
    ArrayList<String> tempData = data;
    
    for (int j = 0; j < tempData.size(); j++) {
    String word = tempData.get(j);
    
    for (int i = 0; i < tempData.size(); i++) {
    
    if (word.equals(tempData.get(i))) {
    count++;
    mode++;
    }
    }
    
    if (!map.containsKey(word)) {
    map.put(word, count);
    } else {
    tempData.remove(word);
    }
    
    count = 0;
    
    //this gets the mode
    if (mode > largestOccurrence) {
    largestOccurrence = mode;
    }
    mode = 0;
    }
    
    }*/
}
