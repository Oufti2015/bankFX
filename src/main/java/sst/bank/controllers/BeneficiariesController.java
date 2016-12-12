package sst.bank.controllers;

import java.util.stream.Collectors;

import org.junit.Assert;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j;
import sst.bank.OuftiBankFX;
import sst.bank.events.BeneficiaryChangeEvent;
import sst.bank.model.Beneficiary;
import sst.bank.model.container.BankContainer;

@Log4j
public class BeneficiariesController {
    @FXML
    private ListView<Beneficiary> beneficiariesListView;
    @FXML
    private EditBeneficiaryController editBeneficiaryController;

    @FXML
    private void initialize() {
	log.debug("initialize...");

	OuftiBankFX.eventBus.register(this);

	Assert.assertNotNull(beneficiariesListView);
	Assert.assertNotNull(editBeneficiaryController);
	ObservableList<Beneficiary> categories = FXCollections.observableArrayList();
	for (Beneficiary category : BankContainer.me().beneficiaries()
		.stream()
		.sorted()
		.collect(Collectors.toList())) {
	    categories.add(category);
	}

	beneficiariesListView.getItems().setAll(categories);
	beneficiariesListView.getSelectionModel().selectedItemProperty().addListener(
		new ChangeListener<Beneficiary>() {
		    @Override
		    public void changed(ObservableValue<? extends Beneficiary> ov,
					Beneficiary old_val, Beneficiary new_val) {
			editBeneficiaryController.setData(new_val);
		    }
		});
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
