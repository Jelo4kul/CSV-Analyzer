package stackedbarchart.tabs;

import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import stackedbarchart.CsvReader;
import stackedbarchart.FileSelector;

/**
 *
 * @author Jelo
 */
public class StartTab {

    private String csvPath;
    private TextArea csvPathArea;
 
    CsvReader csvReader = new CsvReader();
    ObservableList<ObservableList> data = FXCollections.observableArrayList();
    private ArrayList<String> tempKeywords = new ArrayList<>(), keywords = new ArrayList<>();
    private int amtOfCols;

    public VBox createBorderPane() {
        VBox startTabContent = new VBox();
        startTabContent.setId("tab-content");

        Label inpText = createLabel("Input the path to the Csv File, or Select browse");
        startTabContent.getChildren().add(inpText);

        csvPathArea = createTextArea();

        startTabContent.getChildren().add(csvPathArea);

        BorderPane footer = new BorderPane();
        footer.prefWidthProperty().bind(startTabContent.prefWidthProperty());
        footer.setId("footer");

        Button browseBtn = new Button("Browse");
        browseBtn.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Csv File", "*.csv"));

            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    
                    csvPath = file.getAbsolutePath();
                    csvPathArea.setText(csvPath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
            }

        });
        footer.setLeft(browseBtn);

        Button analyzeBtn = new Button("Analyze it");

        analyzeBtn.setOnAction((ActionEvent event) -> {

         
            try {

                for (int i = 0; i < amtOfCols(csvPath); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn("Column" + (i + 1));
                    col.setMinWidth(200);
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    ResultTab.tableView.getColumns().add(col);
                }

                ResultTab.tableView.setItems(data);
                
                //this fills the table with data
                csvReader.printAllColumns(csvPath, amtOfCols, data);

                
                //move to the second tab
                FileSelector.getTabPane().getSelectionModel().select(1);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Please select a file");
            }

            // tempKeywords.clear();
            // keywords.clear();
        });
        footer.setRight(analyzeBtn);

        startTabContent.getChildren().add(footer);
        return startTabContent;
    }

    public String getCsvPath() {
        return csvPath;
    }

    public int amtOfCols(String filePath) {
        amtOfCols = csvReader.countColumns(filePath);
        return amtOfCols;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public Label createLabel(String text) {
        Label inpText = new Label(text);
        inpText.setId("inp-text");
        inpText.setAlignment(Pos.CENTER);
        return inpText;
    }

    public TextArea createTextArea() {
        TextArea thesisText = new TextArea();
        thesisText.setMaxHeight(80);
        thesisText.setPrefWidth(400);
        thesisText.setWrapText(true);
        return thesisText;
    }
}
