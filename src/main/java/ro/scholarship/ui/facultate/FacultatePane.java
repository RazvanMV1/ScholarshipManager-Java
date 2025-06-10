package ro.scholarship.ui.facultate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        Button btnEdit = new Button("Editează");
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaFacultate());
        btnEdit.setOnAction(e -> editeazaFacultate());
        btnDelete.setOnAction(e -> stergeFacultate());
        btnRefresh.setOnAction(e -> refreshTable());

        HBox buttonBar = new HBox(10, btnAdd,btnEdit, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);

        refreshTable();
    }

    private void adaugaFacultate() {
        Dialog<Facultate> dialog = new Dialog<>();
        dialog.setTitle("Adaugă Facultate");
        dialog.setHeaderText("Completează datele pentru facultate:");

        TextField fieldDenumire = new TextField();
        TextField fieldBuget = new TextField();

        fieldBuget.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,9}(\\.\\d{0,2})?")) ? change : null
        ));

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Denumire:"), 0, 0); grid.add(fieldDenumire, 1, 0);
        grid.add(new Label("Buget:"), 0, 1); grid.add(fieldBuget, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    String denumire = fieldDenumire.getText().trim();
                    if (denumire.isEmpty()) throw new IllegalArgumentException("Denumirea e obligatorie!");
                    float buget = Float.parseFloat(fieldBuget.getText().trim());
                    if (buget <= 0) throw new IllegalArgumentException("Bugetul trebuie să fie pozitiv!");
                    Facultate f = new Facultate(denumire);
                    f.setBugetBurse(buget);
                    return f;
                } catch (Exception ex) {
                    showError("Date invalide: " + ex.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(facultate -> {
            Facultate saved = FacultateRestClient.addFacultate(facultate);
            if (saved != null) {
                facultatiList.add(saved);
                table.refresh();
            } else {
                showError("Adăugarea a eșuat!");
            }
        });
    }
    private void editeazaFacultate() {
        Facultate selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează o facultate pentru editare!");
            return;
        }
        Dialog<Facultate> dialog = new Dialog<>();
        dialog.setTitle("Editează Facultate");
        dialog.setHeaderText("Modifică datele facultății:");

        TextField fieldDenumire = new TextField(selected.getDenumire());
        TextField fieldBuget = new TextField(String.valueOf(selected.getBugetBurse()));
        fieldBuget.setTextFormatter(new TextFormatter<>(change ->
                (change.getControlNewText().matches("\\d{0,8}(\\.\\d{0,2})?")) ? change : null
        ));

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Denumire:"), 0, 0); grid.add(fieldDenumire, 1, 0);
        grid.add(new Label("Buget burse:"), 0, 1); grid.add(fieldBuget, 1, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButtonType = new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String denumire = fieldDenumire.getText().trim();
                    String bugetStr = fieldBuget.getText().trim();
                    if (denumire.isEmpty() || bugetStr.isEmpty())
                        throw new IllegalArgumentException("Toate câmpurile sunt obligatorii!");
                    float buget = Float.parseFloat(bugetStr);
                    if (buget < 0) throw new IllegalArgumentException("Bugetul trebuie pozitiv!");
                    selected.setDenumire(denumire);
                    selected.setBugetBurse(buget);
                    return selected;
                } catch (Exception e) {
                    showError("Date incorecte: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(facultate -> {
            if (facultate != null) {
                try {
                    FacultateRestClient.updateFacultate(facultate);
                    refreshTable();
                } catch (Exception e) {
                    showError("Eroare la editare: " + e.getMessage());
                }
            }
        });
    }



    private void stergeFacultate() {
        Facultate selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează o facultate pentru ștergere!");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Sigur vrei să ștergi facultatea \"" + selected.getDenumire() + "\"?", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirmare ștergere");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    FacultateRestClient.deleteFacultate(selected.getId());
                    facultatiList.remove(selected);
                } catch (Exception e) {
                    showError("Eroare la ștergere! " + e.getMessage());
                }
            }
        });
    }

    private void refreshTable() {
        try {
            var lista = FacultateRestClient.loadAllFacultati();
            facultatiList.setAll(lista);
            if (lista.isEmpty()) {
                showError("Nu există facultăți în baza de date!");
            }
        } catch (Exception e) {
            showError("Eroare la încărcarea facultăților: " + e.getMessage());
        }
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
