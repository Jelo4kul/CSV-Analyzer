package stackedbarchart;

import stackedbarchart.tabs.ResultTab;
import stackedbarchart.tabs.StartTab;
import stackedbarchart.tabs.VisualAnalysisTab;

import javafx.application.Application;
import javafx.geometry.Insets;;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Jelo
 */
public class FileSelector extends Application {
    
    private static TabPane tabPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();

        //create the headerLayout
        BorderPane header = createHeader();

        //create our centerpanel
        StackPane centerPanel = createCenterPanel();
        centerPanel.prefWidthProperty().bind(primaryStage.widthProperty());
        centerPanel.prefHeightProperty().bind(primaryStage.heightProperty());

        //add all the created layout to the root layout
        root.getChildren().addAll(header, centerPanel);

        //add the root layout to the scene
        Scene scene = new Scene(root, 1000, 620);

        //attaching the css file to the scene
        scene.getStylesheets().add(FileSelector.class.getResource("styler.css").toExternalForm());

        //attaching the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //method that creates the header panel
    public BorderPane createHeader() {
        BorderPane header = new BorderPane();

        HBox hContent = new HBox(12);
        hContent.setPadding(new Insets(23));
        hContent.setPrefHeight(56);

        header.setRight(hContent);
        header.setId("header");

        Text about = new Text("About");
        about.getStyleClass().add("htext");
        Text contact = new Text("Contact");
        contact.getStyleClass().add("htext");
        Text help = new Text("Help");
        help.getStyleClass().add("htext");

        hContent.getChildren().addAll(about, contact, help);

        return header;
    }

    //layout that contains the center elements
    public StackPane createCenterPanel() {

        //in the centerpanel we have a tabpane that contains four tabas vertically
        StackPane centerPanel = new StackPane();

        tabPane = new TabPane();
        tabPane.setId("inner-tab-pane");
        tabPane.prefHeightProperty().bind(centerPanel.prefHeightProperty());

        //The first tab
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText("Start");
        tab.setContent(new StartTab().createBorderPane());

        //The second tab
        Tab keyword = new Tab();
        keyword.setClosable(false);
        keyword.setText("Result");
        keyword.setContent(new ResultTab().createBorderPane());

        //The third tab
        Tab bulkAnlys = new Tab();
        bulkAnlys.setClosable(false);
        bulkAnlys.setText("Visual Analysis");
        bulkAnlys.setContent(new VisualAnalysisTab().createVisualAnalysisPane());

        tabPane.getTabs().addAll(tab, keyword, bulkAnlys);
        centerPanel.getChildren().add(tabPane);

        return centerPanel;
    }
    
    public static TabPane getTabPane(){
        return tabPane;
    }

}
