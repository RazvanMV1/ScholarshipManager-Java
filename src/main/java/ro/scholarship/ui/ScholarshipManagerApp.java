package ro.scholarship.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ro.scholarship.ui.ManagerBursa.ManagerBursaPane;
import ro.scholarship.ui.student.StudentPane;
import ro.scholarship.ui.facultate.FacultatePane;
import ro.scholarship.ui.specializare.SpecializarePane;
import ro.scholarship.ui.bursa.BursaPane;

public class ScholarshipManagerApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        Tab studentsTab = new Tab("Studenți");
        studentsTab.setContent(new StudentPane());

        Tab facultatiTab = new Tab("Facultăți");
        facultatiTab.setContent(new FacultatePane());

        Tab specializariTab = new Tab("Specializări");
        specializariTab.setContent(new SpecializarePane());

        ManagerBursaPane managerBursaPane = new ManagerBursaPane();
        BursaPane bursaPane = new BursaPane(managerBursaPane);

        Tab burseTab = new Tab("Burse");
        burseTab.setContent(bursaPane);

        Tab managerBursaTab = new Tab("Manager burse");
        managerBursaTab.setContent(managerBursaPane);



        tabPane.getTabs().addAll(studentsTab, facultatiTab, specializariTab, burseTab, managerBursaTab);

        BorderPane root = new BorderPane(tabPane);
        root.setTop(new Label("Scholarship Manager"));

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setTitle("Scholarship Manager – JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
