package ro.scholarship.ui.ManagerBursa;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.ui.rest.BursaRestClient;
import ro.scholarship.ui.rest.ManagerBursaRestClient;

import java.util.List;

public class ManagerBursaPane extends VBox {
    private ComboBox<Bursa> bursaComboBox = new ComboBox<>();
    private Button proceseazaBtn = new Button("Procesează");
    private TableView<BursaAcordata> rezultateTable = new TableView<>();

    public ManagerBursaPane() {
        setSpacing(10);

        // 1. Populează ComboBox-ul cu bursele existente
        List<Bursa> burse = BursaRestClient.loadAllBurse(); // <-- Adaptează dacă ai altă metodă!
        bursaComboBox.setItems(FXCollections.observableArrayList(burse));
        bursaComboBox.setPromptText("Alege bursa...");

        // 2. Butonul de procesare
        proceseazaBtn.setOnAction(e -> {
            Bursa bursa = bursaComboBox.getValue();
            if (bursa != null) {
                List<BursaAcordata> rezultate = ManagerBursaRestClient.proceseazaBursa(bursa.getId());
                rezultateTable.getItems().setAll(rezultate);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Selectează o bursă!");
                alert.showAndWait();
            }
        });

        // 3. Configurează coloanele pentru TableView
        TableColumn<BursaAcordata, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()));

        TableColumn<BursaAcordata, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getStudent().getNumeComplet()
        ));

        TableColumn<BursaAcordata, String> bursaCol = new TableColumn<>("Bursa");
        bursaCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getBursa().getDenumire()
        ));

        TableColumn<BursaAcordata, Number> punctajCol = new TableColumn<>("Punctaj total");
        punctajCol.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(
                data.getValue().getPunctajTotal()
        ));

        TableColumn<BursaAcordata, String> esteActivaCol = new TableColumn<>("Activă");
        esteActivaCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getEsteActiva() == 1 ? "Da" : "Nu"
        ));

        rezultateTable.getColumns().addAll(idCol, studentCol, bursaCol, punctajCol, esteActivaCol);

        // Layout
        getChildren().addAll(
                new Label("Selectează bursa de procesat:"),
                bursaComboBox,
                proceseazaBtn,
                new Label("Rezultate burse acordate:"),
                rezultateTable
        );
    }
}
