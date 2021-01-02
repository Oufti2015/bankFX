package sst.bank.controllers;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.events.BeneficiaryChangeEvent;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

import java.util.stream.Collectors;

@Log4j2
public class BeneficiariesController {
    @FXML
    private ListView<Beneficiary> beneficiariesListView;
    @FXML
    private EditBeneficiaryController editBeneficiaryController;

    @FXML
    private void initialize() {
        log.debug("initialize...");

        OuftiBankFX.eventBus.register(this);

        ObservableList<Beneficiary> categories = FXCollections.observableArrayList();
        for (Beneficiary category : BankContainer.me().beneficiaries()
                .stream()
                .sorted()
                .collect(Collectors.toList())) {
            categories.add(category);
        }

        beneficiariesListView.getItems().setAll(categories);
        beneficiariesListView.getSelectionModel().selectedItemProperty().addListener(
                (ov, oldVal, newVal) -> editBeneficiaryController.setData(newVal));
    }

    @Subscribe
    public void handleEvent(BeneficiaryChangeEvent e) {
        ObservableList<Beneficiary> categories = FXCollections.observableArrayList();
        for (Beneficiary category : BankContainer.me().beneficiaries()
                .stream()
                .sorted()
                .collect(Collectors.toList())) {
            categories.add(category);
        }
        beneficiariesListView.getItems().setAll(categories);
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
        log.debug("Event " + e.getEvent() + " not subscribed...");
    }
}
