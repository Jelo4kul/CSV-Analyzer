package stackedbarchart.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import stackedbarchart.StackedBarChartCreator;

/**
 *
 * @author Jelo
 */
public class VisualAnalysisTab {

    public VBox createVisualAnalysisPane() {
        VBox summaryPane = new VBox();
        summaryPane.setId("tab-content");

        VBox summaryContainer = createBarChartContainer();

        summaryPane.getChildren().add(summaryContainer);

        return summaryPane;
    }

    private VBox createBarChartContainer() {
        VBox container = new VBox();
        container.getChildren().addAll(createHeader(), createContents());
        return container;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #0c3447");

        Label labelName = new Label("COLUMNAR VISUAL ANALYSIS OF DATA");
        labelName.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: #c5d9e2");
        header.getChildren().add(labelName);

        return header;
    }

    private VBox createContents() {
        
        
        VBox contents = new VBox(10);
        contents.setPadding(new Insets(16, 5, 5, 5));
        contents.setStyle("-fx-background-color: #c5d9e2");

        contents.getChildren().addAll(new StackedBarChartCreator().getBarChart());

        return contents;
    }

   
}
