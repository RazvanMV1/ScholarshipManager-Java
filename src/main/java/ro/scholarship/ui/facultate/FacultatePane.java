package ro.scholarship.ui.facultate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ro.scholarship.model.Facultate;
import ro.scholarship.ui.rest.FacultateRestClient;

public class FacultatePane extends BorderPane {

    private TableView<Facultate> table;
    private ObservableList<Facultate> facultatiList;

    public FacultatePane() {
        table = new TableView<>();

        TableColumn<Facultate, String> colDenumire = new TableColumn<>("Denumire");
        colDenumire.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDenumire()));

        TableColumn<Facultate, Number> colBuget = new TableColumn<>("Buget Burse");
        colBuget.setCellValueFactory(data -> new javafx.beans.property.SimpleFloatProperty(data.getValue().getBugetBurse()));

        table.getColumns().addAll(colDenumire, colBuget);

        facultatiList = FXCollections.observableArrayList();
        table.setItems(facultatiList);

        setCenter(table);

        Button btnAdd = new Button("Adaugă");
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaFacultate());
        btnDelete.setOnAction(e -> stergeFacultate());
        btnRefresh.setOnAction(e -> refreshTable());

        HBox buttonBar = new HBox(10, btnAdd, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);

        refreshTable();
    }

    private void adaugaFacultate() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Introdu denumirea facultății:");
        dialog.setTitle("Adaugă Facultate");
        dialog.setContentText("Denumire:");
        dialog.showAndWait().ifPresent(denumire -> {
            TextInputDialog bugetDialog = new TextInputDialog();
            bugetDialog.setHeaderText("Introdu bugetul de burse:");
            bugetDialog.setTitle("Adaugă Facultate");
            bugetDialog.setContentText("Buget:");
            bugetDialog.showAndWait().ifPresent(bugetStr -> {
                try {
                    float buget = Float.parseFloat(bugetStr);
                    Facultate f = new Facultate(denumire);
                    f.setBugetBurse(buget);
                    Facultate saved = FacultateRestClient.addFacultate(f);
                    if (saved != null) {
                        facultatiList.add(saved);
                        table.refresh();
                    } else {
                        showError("Adăugarea a eșuat!");
                    }
                } catch (NumberFormatException ex) {
                    showError("Bugetul trebuie să fie un număr!");
                }
            });
        });
    }

    private void stergeFacultate() {
        Facultate selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            FacultateRestClient.deleteFacultate(selected.getId());
            facultatiList.remove(selected);
        } else {
            showError("Selectează o facultate pentru ștergere!");
        }
    }

    private void refreshTable() {
        facultatiList.setAll(FacultateRestClient.loadAllFacultati());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
