package ro.scholarship.ui.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ro.scholarship.model.Facultate;
import ro.scholarship.model.Specializare;
import ro.scholarship.model.Student;
import ro.scholarship.ui.rest.FacultateRestClient;
import ro.scholarship.ui.rest.SpecializareRestClient;
import ro.scholarship.ui.rest.StudentRestClient;

import java.util.List;

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

        studentList = FXCollections.observableArrayList();
        table.setItems(studentList);

        setCenter(table);

        // Butoane CRUD
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

        // Inițial încarcă din backend
        refreshTable();  // ← Încarcă datele reale din backend imediat după creare
    }

    private void adaugaStudent() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Adaugă Student");
        dialog.setHeaderText("Completează datele pentru noul student:");

        TextField fieldNume = new TextField();
        TextField fieldPrenume = new TextField();
        TextField fieldCNP = new TextField();
        TextField fieldEmail = new TextField();
        TextField fieldTelefon = new TextField();
        TextField fieldAn = new TextField();

        ComboBox<Facultate> comboFacultate = new ComboBox<>();
        comboFacultate.setPromptText("Alege facultatea");
        ComboBox<Specializare> comboSpecializare = new ComboBox<>();
        comboSpecializare.setPromptText("Alege specializarea");

        List<Facultate> facultati = FacultateRestClient.loadAllFacultati();
        comboFacultate.setItems(FXCollections.observableArrayList(facultati));
        comboFacultate.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Facultate item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getDenumire());
            }
        });
        comboFacultate.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Facultate item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getDenumire());
            }
        });

        List<Specializare> specializari = SpecializareRestClient.loadAllSpecializari();
        comboSpecializare.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Specializare item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getDenumire());
            }
        });
        comboSpecializare.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Specializare item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getDenumire());
            }
        });

        comboFacultate.setOnAction(e -> {
            Facultate selected = comboFacultate.getValue();
            if (selected != null) {
                List<Specializare> filtered = specializari.stream()
                        .filter(s -> s.getFacultate().getId() == selected.getId())
                        .toList();
                comboSpecializare.setItems(FXCollections.observableArrayList(filtered));
            } else {
                comboSpecializare.getItems().clear();
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Nume:"), 0, 0);         grid.add(fieldNume, 1, 0);
        grid.add(new Label("Prenume:"), 0, 1);      grid.add(fieldPrenume, 1, 1);
        grid.add(new Label("CNP:"), 0, 2);          grid.add(fieldCNP, 1, 2);
        grid.add(new Label("Email:"), 0, 3);        grid.add(fieldEmail, 1, 3);
        grid.add(new Label("Telefon:"), 0, 4);      grid.add(fieldTelefon, 1, 4);
        grid.add(new Label("An studiu:"), 0, 5);    grid.add(fieldAn, 1, 5);
        grid.add(new Label("Facultate:"), 0, 6);    grid.add(comboFacultate, 1, 6);
        grid.add(new Label("Specializare:"), 0, 7); grid.add(comboSpecializare, 1, 7);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButtonType = new ButtonType("Adaugă", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    int an = Integer.parseInt(fieldAn.getText().trim());
                    Specializare selectedSpecializare = comboSpecializare.getValue();
                    if (selectedSpecializare == null) throw new IllegalArgumentException("Alege specializarea!");
                    return new Student(
                            0,
                            fieldNume.getText().trim(),
                            fieldPrenume.getText().trim(),
                            fieldCNP.getText().trim(),
                            fieldEmail.getText().trim(),
                            fieldTelefon.getText().trim(),
                            selectedSpecializare,
                            an,
                            0.0f,
                            false
                    );
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(student -> {
            if (student != null) {
                try {
                    StudentRestClient.addStudent(student);
                    refreshTable();
                } catch (Exception e) {
                    Alert err = new Alert(Alert.AlertType.ERROR, "Eroare la adăugare student: " + e.getMessage());
                    err.showAndWait();
                }
            }
        });
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
        List<Student> studenti = StudentRestClient.loadAllStudents();
        studentList.setAll(studenti);
        table.refresh();
    }
}
