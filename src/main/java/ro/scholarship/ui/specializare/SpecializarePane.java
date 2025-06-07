package ro.scholarship.ui.specializare;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaSpecializare());
        btnDelete.setOnAction(e -> stergeSpecializare());
        btnRefresh.setOnAction(e -> refreshTable());

        HBox buttonBar = new HBox(10, btnAdd, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);

        refreshTable();
    }

    private void adaugaSpecializare() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Introdu denumirea specializării:");
        dialog.setTitle("Adaugă Specializare");
        dialog.setContentText("Denumire:");
        dialog.showAndWait().ifPresent(denumire -> {
            TextInputDialog locuriDialog = new TextInputDialog();
            locuriDialog.setHeaderText("Introdu numărul de locuri burse:");
            locuriDialog.setTitle("Adaugă Specializare");
            locuriDialog.setContentText("Locuri burse:");
            locuriDialog.showAndWait().ifPresent(locuriStr -> {
                try {
                    int locuri = Integer.parseInt(locuriStr);
                    ro.scholarship.model.Facultate f = new ro.scholarship.model.Facultate();
                    f.setId(1);
                    Specializare spec = new Specializare(denumire, locuri);
                    spec.setFacultate(f);
                    Specializare saved = SpecializareRestClient.addSpecializare(spec);
                    if (saved != null) {
                        specializariList.add(saved);
                        table.refresh();
                    } else {
                        showError("Adăugarea a eșuat!");
                    }
                } catch (NumberFormatException ex) {
                    showError("Numărul de locuri trebuie să fie un număr!");
                }
            });
        });
    }

    private void stergeSpecializare() {
        Specializare selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            SpecializareRestClient.deleteSpecializare(selected.getId());
            specializariList.remove(selected);
        } else {
            showError("Selectează o specializare pentru ștergere!");
        }
    }

    private void refreshTable() {
        specializariList.setAll(SpecializareRestClient.loadAllSpecializari());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
