
package stackedbarchart;

import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author Jelo
 */
public class StackedBChart extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //defining the x axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Africa", "America", "Asia", "Europe", "Oceania")));
        xAxis.setLabel("Category");

        //defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population (in millions)");

        //creating the barchart
        StackedBarChart<String, Number> bChart = new StackedBarChart<>(xAxis, yAxis);
        bChart.setTitle("Historic world population");

        //preparing the series
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("1800");
        series1.getData().add(new XYChart.Data<>("Africa", 107));
        series1.getData().add(new XYChart.Data<>("America", 31));
        series1.getData().add(new XYChart.Data<>("Asia", 635));
        series1.getData().add(new XYChart.Data<>("Europe", 203));
        series1.getData().add(new XYChart.Data<>("Oceania", 54));
        series1.getData().add(new XYChart.Data<>("Oceania", 20));
        series1.getData().add(new XYChart.Data<>("Oceania", 34));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("2000");
        series2.getData().add(new XYChart.Data<>("Africa", 456));
        series2.getData().add(new XYChart.Data<>("America", 31));
        series2.getData().add(new XYChart.Data<>("Asia", 45));
        series2.getData().add(new XYChart.Data<>("Europe", 100));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("2002");
        series3.getData().add(new XYChart.Data<>("Africa", 973));
        series3.getData().add(new XYChart.Data<>("America", 914));
        series3.getData().add(new XYChart.Data<>("Asia", 4054));
        series3.getData().add(new XYChart.Data<>("Europe", 732));

        bChart.getData().addAll(series1, series2, series3);


        //Creating a Group object  
        Group root = new Group();
        root.getChildren().add(bChart);

        //Creating a scene object 
        Scene scene = new Scene(root, 600, 400);

        //Setting title to the Stage 
        primaryStage.setTitle("stackedBarChart");

        //Adding scene to the stage 
        primaryStage.setScene(scene);

        //Displaying the contents of the stage 
        primaryStage.show();

    }
}
