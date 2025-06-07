package ro.scholarship.ui.student;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
        TableColumn<Student, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNume()));

        TableColumn<Student, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenume()));

        TableColumn<Student, String> colCnp = new TableColumn<>("CNP");
        colCnp.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCnp()));

        TableColumn<Student, Integer> colAn = new TableColumn<>("An");
        colAn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAnStudiu()).asObject());

        TableColumn<Student, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<Student, String> colTelefon = new TableColumn<>("Telefon");
        colTelefon.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefon()));

        TableColumn<Student, String> colSpecializare = new TableColumn<>("Specializare");
        colSpecializare.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getSpecializare() != null ? data.getValue().getSpecializare().getDenumire() : "")
        );

        TableColumn<Student, Float> colMedie = new TableColumn<>("Medie sem. anterior");
        colMedie.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().getMedieSemestruAnterior()).asObject());

        table.getColumns().setAll(
                colNume, colPrenume, colCnp, colAn, colEmail, colTelefon, colSpecializare, colMedie
        );


        studentList = FXCollections.observableArrayList();
        table.setItems(studentList);

        setCenter(table);

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

        refreshTable();
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
        TextField fieldMedie = new TextField();

        fieldCNP.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,13}")) ? change : null
        ));
        fieldTelefon.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("0{0,1}7{0,1}\\d{0,8}")) ? change : null
        ));
        fieldAn.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,1}")) ? change : null
        ));
        fieldMedie.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("([0-9]{0,2}(\\.[0-9]{0,2})?)?")) ? change : null
        ));

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
        grid.add(new Label("Medie semestru anterior:"), 0, 6); grid.add(fieldMedie, 1, 6);
        grid.add(new Label("Facultate:"), 0, 8);    grid.add(comboFacultate, 1, 8);
        grid.add(new Label("Specializare:"), 0, 9); grid.add(comboSpecializare, 1, 9);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButtonType = new ButtonType("Adaugă", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);

        addButton.setDisable(true);
        fieldNume.textProperty().addListener((obs, oldV, newV) ->
                addButton.setDisable(newV.trim().isEmpty())
        );

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String nume = fieldNume.getText().trim();
                    String prenume = fieldPrenume.getText().trim();
                    String cnp = fieldCNP.getText().trim();
                    String email = fieldEmail.getText().trim();
                    String telefon = fieldTelefon.getText().trim();
                    String anStr = fieldAn.getText().trim();
                    String medieStr = fieldMedie.getText().trim();

                    if (nume.isEmpty() || prenume.isEmpty() || cnp.isEmpty() || email.isEmpty() ||
                            telefon.isEmpty() || anStr.isEmpty() || medieStr.isEmpty())
                        throw new IllegalArgumentException("Toate câmpurile sunt obligatorii!");

                    if (!cnp.matches("\\d{13}"))
                        throw new IllegalArgumentException("CNP-ul trebuie să conțină exact 13 cifre!");

                    if (!telefon.matches("07\\d{8}"))
                        throw new IllegalArgumentException("Telefonul trebuie să înceapă cu 07 și să aibă 10 cifre!");

                    if (!email.contains("@"))
                        throw new IllegalArgumentException("Email-ul nu este valid!");

                    int an = Integer.parseInt(anStr);
                    if (an < 1 || an > 4)
                        throw new IllegalArgumentException("Anul de studiu trebuie să fie între 1 și 4!");

                    float medie = Float.parseFloat(medieStr);
                    if (medie < 1.0f || medie > 10.0f)
                        throw new IllegalArgumentException("Media trebuie să fie între 1.0 și 10.0!");

                    Specializare selectedSpecializare = comboSpecializare.getValue();
                    if (selectedSpecializare == null)
                        throw new IllegalArgumentException("Alege specializarea!");

                    return new Student(
                            0,
                            nume,
                            prenume,
                            cnp,
                            email,
                            telefon,
                            selectedSpecializare,
                            an,
                            medie
                    );
                } catch (Exception e) {
                    Alert err = new Alert(Alert.AlertType.ERROR, "Date incorecte: " + e.getMessage());
                    err.showAndWait();
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
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Sigur vrei să ștergi studentul selectat?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        StudentRestClient.deleteStudent(selected.getId());
                        refreshTable();
                    } catch (Exception e) {
                        Alert err = new Alert(Alert.AlertType.ERROR, "Eroare la ștergere: " + e.getMessage());
                        err.showAndWait();
                    }
                }
            });
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
