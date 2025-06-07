package ro.scholarship.ui.specializare;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ro.scholarship.model.Specializare;
import ro.scholarship.ui.rest.SpecializareRestClient;

public class SpecializarePane extends BorderPane {

    private TableView<Specializare> table;
    private ObservableList<Specializare> specializariList;

    public SpecializarePane() {
        table = new TableView<>();

        TableColumn<Specializare, String> colDenumire = new TableColumn<>("Denumire");
        colDenumire.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDenumire()));

        TableColumn<Specializare, Number> colLocuri = new TableColumn<>("Locuri burse");
        colLocuri.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumarLocuriBurse()));

        TableColumn<Specializare, String> colFacultate = new TableColumn<>("Facultate");
        colFacultate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getFacultate() != null ? data.getValue().getFacultate().getDenumire() : "N/A"
        ));

        table.getColumns().addAll(colDenumire, colLocuri, colFacultate);

        specializariList = FXCollections.observableArrayList();
        table.setItems(specializariList);

        setCenter(table);

        Button btnAdd = new Button("Adaugă");
        Button btnEdit = new Button("Editează");
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaSpecializare());
        btnEdit.setOnAction(e -> editeazaSpecializare());
        btnDelete.setOnAction(e -> stergeSpecializare());
        btnRefresh.setOnAction(e -> refreshTable());

        HBox buttonBar = new HBox(10, btnAdd,btnEdit, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);

        refreshTable();
    }

    private void adaugaSpecializare() {
        Dialog<Specializare> dialog = new Dialog<>();
        dialog.setTitle("Adaugă Specializare");
        dialog.setHeaderText("Completează datele pentru noua specializare:");

        TextField fieldDenumire = new TextField();
        TextField fieldLocuri = new TextField();

        fieldLocuri.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,4}")) ? change : null
        ));

        ComboBox<ro.scholarship.model.Facultate> comboFacultate =
                new ComboBox<>(FXCollections.observableArrayList(
                        ro.scholarship.ui.rest.FacultateRestClient.loadAllFacultati()));
        comboFacultate.setPromptText("Alege facultatea");

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Denumire:"), 0, 0); grid.add(fieldDenumire, 1, 0);
        grid.add(new Label("Locuri burse:"), 0, 1); grid.add(fieldLocuri, 1, 1);
        grid.add(new Label("Facultate:"), 0, 2); grid.add(comboFacultate, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButtonType = new ButtonType("Adaugă", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    String denumire = fieldDenumire.getText().trim();
                    String locuriStr = fieldLocuri.getText().trim();
                    var facultate = comboFacultate.getValue();

                    if (denumire.isEmpty() || locuriStr.isEmpty() || facultate == null)
                        throw new IllegalArgumentException("Toate câmpurile sunt obligatorii!");

                    int locuri = Integer.parseInt(locuriStr);
                    if (locuri < 1) throw new IllegalArgumentException("Numărul de locuri trebuie să fie pozitiv!");

                    Specializare spec = new Specializare(denumire, locuri);
                    spec.setFacultate(facultate);
                    return spec;
                } catch (Exception e) {
                    Alert err = new Alert(Alert.AlertType.ERROR, "Date incorecte: " + e.getMessage());
                    err.showAndWait();
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(spec -> {
            if (spec != null) {
                Specializare saved = SpecializareRestClient.addSpecializare(spec);
                if (saved != null) {
                    specializariList.add(saved);
                    table.refresh();
                } else {
                    showError("Adăugarea a eșuat!");
                }
            }
        });
    }

    private void editeazaSpecializare() {
        Specializare selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează o specializare pentru editare!");
            return;
        }
        Dialog<Specializare> dialog = new Dialog<>();
        dialog.setTitle("Editează Specializare");
        dialog.setHeaderText("Modifică datele specializării:");

        TextField fieldDenumire = new TextField(selected.getDenumire());
        TextField fieldLocuri = new TextField(String.valueOf(selected.getNumarLocuriBurse()));
        fieldLocuri.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,4}")) ? change : null
        ));

        ComboBox<ro.scholarship.model.Facultate> comboFacultate =
                new ComboBox<>(FXCollections.observableArrayList(
                        ro.scholarship.ui.rest.FacultateRestClient.loadAllFacultati()));
        comboFacultate.setValue(selected.getFacultate());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Denumire:"), 0, 0); grid.add(fieldDenumire, 1, 0);
        grid.add(new Label("Locuri burse:"), 0, 1); grid.add(fieldLocuri, 1, 1);
        grid.add(new Label("Facultate:"), 0, 2); grid.add(comboFacultate, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String denumire = fieldDenumire.getText().trim();
                    String locuriStr = fieldLocuri.getText().trim();
                    var facultate = comboFacultate.getValue();
                    if (denumire.isEmpty() || locuriStr.isEmpty() || facultate == null)
                        throw new IllegalArgumentException("Toate câmpurile sunt obligatorii!");
                    int locuri = Integer.parseInt(locuriStr);
                    if (locuri < 1) throw new IllegalArgumentException("Numărul de locuri trebuie să fie pozitiv!");
                    selected.setDenumire(denumire);
                    selected.setNumarLocuriBurse(locuri);
                    selected.setFacultate(facultate);
                    return selected;
                } catch (Exception e) {
                    showError("Date incorecte: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(specializare -> {
            if (specializare != null) {
                try {
                    SpecializareRestClient.updateSpecializare(specializare);
                    refreshTable();
                } catch (Exception e) {
                    showError("Eroare la editare: " + e.getMessage());
                }
            }
        });
    }


    private void stergeSpecializare() {
        Specializare selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmare ștergere");
            confirm.setHeaderText("Ești sigur că vrei să ștergi specializarea \"" + selected.getDenumire() + "\"?");

            ButtonType btnYes = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnNo = new ButtonType("Nu", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(btnYes, btnNo);

            confirm.showAndWait().ifPresent(type -> {
                if (type == btnYes) {
                    SpecializareRestClient.deleteSpecializare(selected.getId());
                    specializariList.remove(selected);
                }
            });
        } else {
            showError("Selectează o specializare pentru ștergere!");
        }
    }


    private void refreshTable() {
        try {
            specializariList.setAll(SpecializareRestClient.loadAllSpecializari());
        } catch (Exception e) {
            showError("Eroare la încărcare specializări: " + e.getMessage());
        }
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}