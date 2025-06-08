package ro.scholarship.ui.bursa;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.TipBursa;
import ro.scholarship.model.SemestruUniversitar;
import ro.scholarship.ui.ManagerBursa.ManagerBursaPane;
import ro.scholarship.ui.rest.BursaRestClient;
import ro.scholarship.ui.rest.SemestruRestClient;

public class BursaPane extends BorderPane {

    private final TableView<Bursa> table;
    private final ObservableList<Bursa> burseList;
    private final ManagerBursaPane managerBursaPane; // referință pentru refresh

    public BursaPane(ManagerBursaPane managerBursaPane) {
        this.managerBursaPane = managerBursaPane;
        table = new TableView<>();

        TableColumn<Bursa, String> colDenumire = new TableColumn<>("Denumire");
        colDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDenumire()));

        TableColumn<Bursa, String> colTip = new TableColumn<>("Tip");
        colTip.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getTip() != null ? data.getValue().getTip().toString() : "N/A"));

        TableColumn<Bursa, Number> colValoare = new TableColumn<>("Valoare");
        colValoare.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().getValoare()));

        TableColumn<Bursa, Integer> colNumar = new TableColumn<>("Număr");
        colNumar.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNumarBurseDisponibile()).asObject());

        TableColumn<Bursa, SemestruUniversitar> colSemestru = new TableColumn<>("Semestru");
        colSemestru.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getSemestru()));
        colSemestru.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(SemestruUniversitar item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText("");
                else setText(item.getAnUniversitar() + "/S" + item.getSemestru() + " (" + item.getDataInceput() + " - " + item.getDataSfarsit() + ")");
            }
        });

        table.getColumns().addAll(colDenumire, colTip, colValoare, colNumar, colSemestru);

        burseList = FXCollections.observableArrayList();
        table.setItems(burseList);

        setCenter(table);

        Button btnAdd = new Button("Adaugă");
        Button btnEdit = new Button("Editează");
        Button btnDelete = new Button("Șterge");
        Button btnRefresh = new Button("Refresh");

        btnAdd.setOnAction(e -> adaugaSauEditeazaBursa(null));
        btnEdit.setOnAction(e -> {
            Bursa selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) adaugaSauEditeazaBursa(selected);
            else showError("Selectează o bursă pentru editare!");
        });
        btnDelete.setOnAction(e -> stergeBursa());
        btnRefresh.setOnAction(e -> refreshTableAndManager());

        HBox buttonBar = new HBox(10, btnAdd, btnEdit, btnDelete, btnRefresh);
        buttonBar.setPadding(new Insets(10));
        setBottom(buttonBar);

        refreshTableAndManager();
    }

    private void adaugaSauEditeazaBursa(Bursa bursa) {
        boolean isEdit = (bursa != null);

        Dialog<Bursa> dialog = new Dialog<>();
        dialog.setTitle(isEdit ? "Editează Bursă" : "Adaugă Bursă");
        dialog.setHeaderText(isEdit ? "Modifică datele bursei:" : "Completează datele pentru noua bursă:");

        TextField fieldDenumire = new TextField(isEdit ? bursa.getDenumire() : "");
        ComboBox<TipBursa> comboTip = new ComboBox<>(FXCollections.observableArrayList(TipBursa.values()));
        comboTip.setValue(isEdit && bursa.getTip() != null ? bursa.getTip() : TipBursa.MERIT);

        TextField fieldValoare = new TextField(isEdit ? String.valueOf(bursa.getValoare()) : "");
        TextField fieldNumar = new TextField(isEdit ? String.valueOf(bursa.getNumarBurseDisponibile()) : "");

        ComboBox<SemestruUniversitar> comboSemestru = new ComboBox<>(
                FXCollections.observableArrayList(SemestruRestClient.loadAllSemestre())
        );
        comboSemestru.setConverter(new StringConverter<>() {
            @Override
            public String toString(SemestruUniversitar s) {
                return s == null ? "" : s.getAnUniversitar() + "/S" + s.getSemestru();
            }
            @Override
            public SemestruUniversitar fromString(String s) { return null; }
        });
        if (isEdit && bursa.getSemestru() != null)
            comboSemestru.setValue(bursa.getSemestru());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 120, 10, 10));
        grid.add(new Label("Denumire:"), 0, 0); grid.add(fieldDenumire, 1, 0);
        grid.add(new Label("Tip:"), 0, 1); grid.add(comboTip, 1, 1);
        grid.add(new Label("Valoare:"), 0, 2); grid.add(fieldValoare, 1, 2);
        grid.add(new Label("Număr burse:"), 0, 3); grid.add(fieldNumar, 1, 3);
        grid.add(new Label("Semestru:"), 0, 4); grid.add(comboSemestru, 1, 4);

        dialog.getDialogPane().setContent(grid);
        ButtonType saveButtonType = new ButtonType(isEdit ? "Salvează" : "Adaugă", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String denumire = fieldDenumire.getText().trim();
                    TipBursa tip = comboTip.getValue();
                    float valoare = Float.parseFloat(fieldValoare.getText().trim());
                    int numar = Integer.parseInt(fieldNumar.getText().trim());
                    SemestruUniversitar semestru = comboSemestru.getValue();

                    if (denumire.isEmpty()) throw new IllegalArgumentException("Denumirea este obligatorie!");
                    if (valoare <= 0) throw new IllegalArgumentException("Valoarea trebuie să fie pozitivă!");
                    if (numar <= 0) throw new IllegalArgumentException("Numărul trebuie să fie pozitiv!");
                    if (semestru == null) throw new IllegalArgumentException("Alege un semestru!");

                    Bursa nouaBursa = isEdit ? bursa : new Bursa();
                    nouaBursa.setDenumire(denumire);
                    nouaBursa.setTip(tip);
                    nouaBursa.setValoare(valoare);
                    nouaBursa.setNumarBurseDisponibile(numar);
                    nouaBursa.setSemestru(semestru);
                    return nouaBursa;
                } catch (Exception ex) {
                    showError("Date incorecte: " + ex.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            try {
                if (isEdit) {
                    BursaRestClient.updateBursa(result);
                } else {
                    Bursa saved = BursaRestClient.addBursa(result);
                    if (saved != null) burseList.add(saved);
                }
                refreshTableAndManager();
            } catch (Exception ex) {
                showError("Eroare la salvare: " + ex.getMessage());
            }
        });
    }

    private void stergeBursa() {
        Bursa selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selectează o bursă pentru ștergere!");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Sigur vrei să ștergi bursa \"" + selected.getDenumire() + "\"?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Confirmare ștergere");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    BursaRestClient.deleteBursa(selected.getId());
                    refreshTableAndManager();
                } catch (Exception e) {
                    showError("Eroare la ștergere! " + e.getMessage());
                }
            }
        });
    }

    // Dă refresh atât la tabel cât și la manager pane (ca să se vadă la procesare instant)
    private void refreshTableAndManager() {
        burseList.setAll(BursaRestClient.loadAllBurse());
        if (managerBursaPane != null) {
            managerBursaPane.refreshBurse();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
