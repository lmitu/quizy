package pl.mitusinski;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {

    private Main main;

    @FXML
    private TableView<Quiz> tableView;

    @FXML
    private TableColumn<Quiz, String> idColumn;

    @FXML
    private TableColumn<Quiz, String> titleColumn;

    @FXML
    private TextField searchQuery;

    @FXML
    private void initialize() {
    }

    public void setMainHandle(Main main) {
        this.main = main;
        updateList();
    }

    public void setPrimaryStage(Stage primaryStage) {
        Stage primaryStage1 = primaryStage;
    }

    public void updateList() {
        ObservableList<Quiz> observableQuizList = main.getQuizList().getObservableQuizList();
        updateTableData(observableQuizList);
    }

    public void openInBrowser() {
        try {
            int id = Integer.parseInt(tableView.getSelectionModel().getSelectedItem().getId());
            Desktop.getDesktop().browse(new URI(QuizUpdater.getUrl(id)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void filterList() {
        String filterString = searchQuery.getText();
        updateTableData(main.getQuizList().getObservableFilteredQuizList(filterString));
    }

    public void refreshList() {
        QuizUpdater quizUpdater = new QuizUpdater();
        main.performQuizUpdate(quizUpdater, this);
        quizUpdater.update();
    }

    private void updateTableData(ObservableList<Quiz> observableList) {
        tableView.setItems(observableList);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
    }

    public void polishOnly(ActionEvent actionEvent) {
        updateTableData(main.getQuizList().getObservableQuizListFilteredByLanguage("PL"));
    }

    public void spanishOnly(ActionEvent actionEvent) {
        updateTableData(main.getQuizList().getObservableQuizListFilteredByLanguage("ES"));
    }
}
