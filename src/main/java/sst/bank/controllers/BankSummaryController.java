package sst.bank.controllers;

import java.util.Collection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import lombok.extern.log4j.Log4j;
import sst.bank.model.BankSummary;

@Log4j
public class BankSummaryController {
    @FXML
    private Label byLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private Label summaryLabel;
    @FXML
    ListView<BankSummary> listViewByMonth;
    @FXML
    OperationsListController operationsByMonthController;

    @FXML
    private void initialize() {
	log.info("initialize...");
    }

    public void setTitle(String title) {
	this.byLabel.setText(title);
    }

    public void setTreeViewData(Collection<BankSummary> list) {
	ObservableList<BankSummary> items = FXCollections.observableArrayList();
	for (BankSummary item : list) {
	    items.add(item);
	}
	listViewByMonth.setItems(items);
	listViewByMonth.setEditable(true);
	listViewByMonth.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	listViewByMonth.getSelectionModel().selectedItemProperty().addListener(
		new ChangeListener<BankSummary>() {
		    @Override
		    public void changed(ObservableValue<? extends BankSummary> ov,
					BankSummary old_val, BankSummary new_val) {
			log.debug("" + old_val + "-" + new_val);
		    }
		});
    }
}
