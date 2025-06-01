package ro.scholarship.ui.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ro.scholarship.model.Student;

public class StudentPane extends BorderPane {

    private TableView<Student> table;
    private ObservableList<Student> studentList;

    public StudentPane() {
        table = new TableView<>();

        // Coloane
        TableColumn<Student, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNume()));

        TableColumn<Student, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenume()));

        TableColumn<Student, String> colCnp = new TableColumn<>("CNP");
        colCnp.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCnp()));

        TableColumn<Student, Integer> colAn = new TableColumn<>("An");
        colAn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAnStudiu()).asObject());

        table.getColumns().addAll(colNume, colPrenume, colCnp, colAn);

        // Date mock pentru testare UI
        studentList = FXCollections.observableArrayList(
                new Student("Popescu", "Ion", "1980101012345", 1),
                new Student("Ionescu", "Maria", "2980101012346", 2)
        );
        table.setItems(studentList);

        setCenter(table);

        // Butoane CRUD (deocamdată doar funcționalitate minimă)
        Button btnAdd = new Button("Adaugă");
        Button btnEdit = new Button("Editează");
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaStudent());
        btnEdit.setOnAction(e -> editeazaStudent());
        btnDelete.setOnAction(e -> stergeStudent());
        btnRefresh.setOnAction(e -> refreshTable());

        HBox buttonBar = new HBox(10, btnAdd, btnEdit, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);
    }

    private void adaugaStudent() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Funcționalitatea de adăugare va fi implementată.");
        alert.showAndWait();
    }

    private void editeazaStudent() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Funcționalitatea de editare va fi implementată.");
        alert.showAndWait();
    }

    private void stergeStudent() {
        Student selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            studentList.remove(selected);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selectează un student pentru ștergere!");
            alert.showAndWait();
        }
    }

    private void refreshTable() {
        // În viitor va reîncărca de la REST API
        studentList.setAll(ro.scholarship.ui.rest.StudentRestClient.loadAllStudents());
    }
}
