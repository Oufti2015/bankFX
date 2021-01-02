package sst.bank.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.events.BeneficiaryChangeEvent;
import sst.bank.model.BankSummary;
import sst.bank.model.Beneficiary;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

import java.util.stream.Collectors;

@Log4j2
public class EditBeneficiaryController {
    private Beneficiary beneficiary;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ListView<String> counterpartiesListView;
    @FXML
    private ListView<String> detailsListView;
    @FXML
    private OperationsListController operationsByBeneficiaryController;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button mergeIntoButton;
    @FXML
    private ComboBox<Beneficiary> mergeIntoComboBox;

    @FXML
    private void initialize() {
        log.debug("initialize...");
        setEditable(false);

        okButton.setOnAction(e -> {
            beneficiary.setName(nameTextField.getText());
            beneficiary.setId(idTextField.getText());

            OuftiBankFX.eventBus.post(new BeneficiaryChangeEvent(beneficiary));
            setEditable(false);
        });

        cancelButton.setOnAction(e -> setCategoryInfo());
    }

    private void setEditable(boolean b) {
        nameTextField.setDisable(!b);
        idTextField.setDisable(!b);
        okButton.setDisable(!b);
        cancelButton.setDisable(!b);
        mergeIntoButton.setDisable(!b);
        mergeIntoComboBox.setDisable(!b);
    }

    public void setData(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;

        if (beneficiary != null) {
            setCategoryInfo();
            setEditable(true);
        }
    }

    private void setCategoryInfo() {
        nameTextField.setText(beneficiary.getName());
        idTextField.setText(beneficiary.getId());
        counterpartiesListView.getItems().setAll(beneficiary.getCounterparties());
        detailsListView.getItems().setAll(beneficiary.getDetails());

        ObservableList<Operation> operations = FXCollections.observableArrayList();
        for (Operation operation : BankContainer.me().operationsContainer().operations()
                .stream()
                .filter(o -> beneficiary.getCounterparties().contains(o.getCounterparty())
                        || beneficiary.getDetails().contains(o.getCounterparty()))
                .collect(Collectors.toList())) {
            operations.add(operation);
        }

        operationsByBeneficiaryController.setData(new BankSummary(operations));

        mergeIntoComboBox.getItems().setAll(BankContainer.me().beneficiaries()
                .stream()
                .sorted()
                .collect(Collectors.toList()));
    }

    @FXML
    public void mergeInto() {
        log.debug("Merge into...");
        Beneficiary from = beneficiary;
        Beneficiary into = mergeIntoComboBox.getSelectionModel().getSelectedItem();

        log.debug("Merge from " + from);
        log.debug("Merge into " + into);

        if (into != null && from != null) {
            into.getCounterparties().addAll(from.getCounterparties());
            into.getDetails().addAll(from.getDetails());

            BankContainer.me().beneficiaries().remove(from);
            setEditable(false);
            OuftiBankFX.eventBus.post(new BeneficiaryChangeEvent(into));
        }
    }
}
