package sst.bank.controllers;

import java.util.stream.Collectors;

import org.junit.Assert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j;
import sst.bank.model.BankSummary;
import sst.bank.model.Beneficiary;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

@Log4j
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
    private void initialize() {
	log.info("initialize...");
	Assert.assertNotNull(nameTextField);
	Assert.assertNotNull(okButton);
	Assert.assertNotNull(cancelButton);

	setEditable(false);

	okButton.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent e) {
		beneficiary.setName(nameTextField.getText());
		beneficiary.setId(idTextField.getText());

		// EventBus eb = new EventBus();
		// OuftiBankFX.eventBus.post(new CategoryChangeEvent(category));
		setEditable(false);
	    }
	});

	cancelButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
		setCategoryInfo();
	    }
	});
    }

    private void setEditable(boolean b) {
	nameTextField.setDisable(!b);
	idTextField.setDisable(!b);
	okButton.setDisable(!b);
	cancelButton.setDisable(!b);
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
	for (Operation operation : BankContainer.me().operations()
		.stream()
		.filter(o -> beneficiary.getCounterparties().contains(o.getCounterparty())
			|| beneficiary.getDetails().contains(o.getCounterparty()))
		.collect(Collectors.toList())) {
	    operations.add(operation);
	}

	operationsByBeneficiaryController.setData(new BankSummary(operations));
    }
}
