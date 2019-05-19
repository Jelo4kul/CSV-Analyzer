package stackedbarchart.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import stackedbarchart.CsvReader;
import stackedbarchart.FileSelector;
import stackedbarchart.StackedBarChartCreator;

/**
 *
 * @author Jelo
 */
public class ResultTab {

    static TableView tableView = new TableView();
    Node temp;
    int previousElementDisplayed;

    StackPane centerLayoutPane = new StackPane();

    public VBox createBorderPane() {
        VBox resultTabContent = new VBox(30);
        resultTabContent.setId("tab-content");
        resultTabContent.setAlignment(Pos.TOP_CENTER);

        StackPane divider = new StackPane();
        divider.setId("divider");
        divider.setAlignment(Pos.TOP_LEFT);

        Label inpText = createLabel("Detected Results");
        divider.getChildren().add(inpText);

        centerLayoutPane.getChildren().add(tableView);

        HBox footer = new HBox(20);
        footer.setId("opt-footer");

        footer.setAlignment(Pos.CENTER);

        Button summaryBtn = new Button("Visualize Data");
        summaryBtn.setId("result-button");
        summaryBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new CsvReader().printSpecificColumn(1);
                long startTime = System.nanoTime();
                new StackedBarChartCreator().populateBarChart();
                long endTime = System.nanoTime();
                double seconds = (endTime - startTime) / 1000000000.0;
                System.out.println("Took:" + seconds + "seconds");
                FileSelector.getTabPane().getSelectionModel().select(2);
            }

        });
        HBox.setMargin(footer, new Insets(110, 0, 0, 0));
        footer.getChildren().addAll(summaryBtn);

        //resultTabContent.setBottom(footer);
        resultTabContent.getChildren().addAll(divider, centerLayoutPane, footer);

        return resultTabContent;
    }

    public Label createLabel(String text) {
        Label inpText = new Label(text);
        inpText.setId("bulk-text");
        return inpText;
    }

}
