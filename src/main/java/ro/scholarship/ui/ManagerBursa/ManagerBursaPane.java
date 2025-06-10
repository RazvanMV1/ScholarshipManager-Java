package ro.scholarship.ui.ManagerBursa;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.ui.rest.BursaRestClient;
import ro.scholarship.ui.rest.ManagerBursaRestClient;

import java.util.List;

public class ManagerBursaPane extends VBox {
    private final ComboBox<Bursa> bursaComboBox = new ComboBox<>();
    private final Button proceseazaBtn = new Button("Procesează");
    private final TableView<BursaAcordata> rezultateTable = new TableView<>();

    private final Label lblDenumire = new Label();
    private final Label lblTip = new Label();
    private final Label lblValoare = new Label();
    private final Label lblSemestru = new Label();
    private final Label lblPerioada = new Label();

    public ManagerBursaPane() {
        setSpacing(16);
        setPadding(new Insets(16));

        refreshBurse();

        bursaComboBox.setPromptText("Alege bursa...");

        bursaComboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Bursa bursa, boolean empty) {
                super.updateItem(bursa, empty);
                setText(empty || bursa == null ? "" : bursa.getDenumire());
            }
        });
        bursaComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Bursa bursa, boolean empty) {
                super.updateItem(bursa, empty);
                setText(empty || bursa == null ? "" : bursa.getDenumire());
            }
        });

        VBox detaliiCard = new VBox(3, lblDenumire, lblTip, lblValoare, lblSemestru, lblPerioada);
        detaliiCard.setPadding(new Insets(10, 14, 10, 14));
        detaliiCard.setStyle("-fx-background-color: #F5F5F5; -fx-border-color: #DDDDDD; -fx-border-radius: 7; -fx-background-radius: 7;");

        detaliiCard.setVisible(false);

        bursaComboBox.setOnAction(e -> {
            Bursa b = bursaComboBox.getValue();
            if (b != null) {
                lblDenumire.setText("Denumire:  " + b.getDenumire());
                lblTip.setText("Tip:         " + b.getTip());
                lblValoare.setText("Valoare:    " + b.getValoare() + " RON");
                lblSemestru.setText("Semestru:   " +
                        (b.getSemestru() != null ? b.getSemestru().getAnUniversitar() + " / " + b.getSemestru().getSemestru() : "-"));
                lblPerioada.setText("Perioadă:   " +
                        (b.getSemestru() != null ? b.getSemestru().getDataInceput() + " - " + b.getSemestru().getDataSfarsit() : "-"));
                detaliiCard.setVisible(true);
            } else {
                detaliiCard.setVisible(false);
            }
        });

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

        TableColumn<BursaAcordata, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> Bindings.createIntegerBinding(data.getValue()::getId));

        TableColumn<BursaAcordata, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(data -> Bindings.createStringBinding(
                () -> data.getValue().getStudent().getNumeComplet()));

        TableColumn<BursaAcordata, String> bursaCol = new TableColumn<>("Bursa");
        bursaCol.setCellValueFactory(data -> Bindings.createStringBinding(
                () -> data.getValue().getBursa().getDenumire()));

        TableColumn<BursaAcordata, Number> punctajCol = new TableColumn<>("Punctaj total");
        punctajCol.setCellValueFactory(data -> Bindings.createObjectBinding(
                () -> (double) data.getValue().getPunctajTotal()));

        TableColumn<BursaAcordata, String> esteActivaCol = new TableColumn<>("Activă");
        esteActivaCol.setCellValueFactory(data -> Bindings.createStringBinding(
                () -> data.getValue().getEsteActiva() == 1 ? "Da" : "Nu"));

        rezultateTable.getColumns().addAll(idCol, studentCol, bursaCol, punctajCol, esteActivaCol);

        getChildren().addAll(
                new Label("Selectează bursa de procesat:"),
                bursaComboBox,
                detaliiCard,
                proceseazaBtn,
                new Separator(),
                new Label("Rezultate burse acordate:"),
                rezultateTable
        );
    }

    public void refreshBurse() {
        List<Bursa> burse = BursaRestClient.loadAllBurse();
        bursaComboBox.setItems(FXCollections.observableArrayList(burse));
        bursaComboBox.getSelectionModel().clearSelection();
    }
}
