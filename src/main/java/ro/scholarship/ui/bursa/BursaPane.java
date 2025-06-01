package ro.scholarship.ui.bursa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ro.scholarship.model.Bursa;
import ro.scholarship.ui.rest.BursaRestClient;

public class BursaPane extends BorderPane {

    private TableView<Bursa> table;
    private ObservableList<Bursa> bursasList;

    public BursaPane() {
        table = new TableView<>();

        TableColumn<Bursa, String> colDenumire = new TableColumn<>("Denumire");
        colDenumire.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDenumire()));

        TableColumn<Bursa, String> colTip = new TableColumn<>("Tip");
        colTip.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getTip() != null ? data.getValue().getTip().toString() : "N/A"));

        TableColumn<Bursa, Number> colValoare = new TableColumn<>("Valoare");
        colValoare.setCellValueFactory(data -> new javafx.beans.property.SimpleFloatProperty(data.getValue().getValoare()));

        TableColumn<Bursa, Integer> colNumar = new TableColumn<>("Număr");
        colNumar.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumarBurseDisponibile()).asObject());

        TableColumn<Bursa, String> colSemestru = new TableColumn<>("Semestru");
        colSemestru.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getSemestru() != null ? data.getValue().getSemestru().toString() : "N/A"));

        table.getColumns().addAll(colDenumire, colTip, colValoare, colNumar, colSemestru);

        bursasList = FXCollections.observableArrayList();
        table.setItems(bursasList);

        setCenter(table);

        Button btnAdd = new Button("Adaugă");
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaBursa());
        btnDelete.setOnAction(e -> stergeBursa());
        btnRefresh.setOnAction(e -> refreshTable());

        HBox buttonBar = new HBox(10, btnAdd, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);

        refreshTable();
    }

    private void adaugaBursa() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Introdu denumirea bursei:");
        dialog.setTitle("Adaugă Bursă");
        dialog.setContentText("Denumire:");
        dialog.showAndWait().ifPresent(denumire -> {
            try {
                // Exemplu simplificat pentru demo (poți extinde cu dialoguri pentru toate câmpurile)
                Bursa b = new Bursa();
                b.setDenumire(denumire);
                b.setTip(ro.scholarship.model.TipBursa.MERIT);
                b.setValoare(1000);
                b.setNumarBurseDisponibile(10);
                // Poți seta și semestrul dacă vrei
                Bursa saved = BursaRestClient.addBursa(b);
                if (saved != null) {
                    bursasList.add(saved);
                    table.refresh();
                } else {
                    showError("Adăugarea a eșuat!");
                }
            } catch (Exception ex) {
                showError("Eroare la adăugare: " + ex.getMessage());
            }
        });
    }

    private void stergeBursa() {
        Bursa selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            BursaRestClient.deleteBursa(selected.getId());
            bursasList.remove(selected);
        } else {
            showError("Selectează o bursă pentru ștergere!");
        }
    }

    private void refreshTable() {
        bursasList.setAll(BursaRestClient.loadAllBurse());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
