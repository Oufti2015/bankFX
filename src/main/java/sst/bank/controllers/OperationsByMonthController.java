package sst.bank.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j;
import sst.bank.OuftiBankFX;
import sst.bank.controllers.utils.DescendingBankSummaryComparator;
import sst.bank.model.BankSummary;
import sst.bank.model.container.BankContainer;

@Log4j
public class OperationsByMonthController {
    @FXML
    private Label byLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private Label summaryLabel;
    @FXML
    private ListView<BankSummary> listViewByMonth;
    @FXML
    private OperationsListController operationsByMonthController;
    @FXML
    private SummaryListController summaryListController;
    @FXML
    private TotalController totalController;

    @FXML
    private void initialize() {
	log.info("initialize...");

	log.debug("aController " + summaryListController);

	if (summaryListController == null) {
	    log.fatal("aController is not initialised...");
	    OuftiBankFX.eventBus.post(new Exception("Controller not injected..."));
	}

	listViewByMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BankSummary>() {

	    @Override
	    public void changed(ObservableValue<? extends BankSummary> observable, BankSummary oldValue,
				BankSummary newValue) {
		// Your action here
		log.debug("Selected item: " + newValue);
		operationsByMonthController.setData(newValue);
		fromLabel.setText("From " + newValue.getStartDate() + " to " + newValue.getEndDate());
		summaryListController.setData(newValue);

		totalController.setOperations(BankContainer.me().operations().size());
		totalController.setOperationsMonth(newValue.operationsCount());
		Double total = newValue.getSummary().values()
			.stream()
			.mapToDouble(o -> o.doubleValue())
			.sum();
		totalController.setTotal(total);
		Double budget = BankContainer.me().getCategories()
			.stream()
			.mapToDouble(c -> c.getBudget().getControlledAmount().doubleValue())
			.sum();
		totalController.setBudget(budget);
		totalController.setResult(total - budget);
		totalController.setCreationDate(BankContainer.me().getCreationDate());
	    }
	});
    }

    public void setTitle(String title) {
	this.byLabel.setText(title);
    }

    public void setListViewData(Collection<BankSummary> list) {
	ObservableList<BankSummary> items = FXCollections.observableArrayList();
	for (BankSummary item : list
		.stream()
		.sorted(new DescendingBankSummaryComparator())
		.collect(Collectors.toList())) {
	    items.add(item);
	}
	listViewByMonth.setItems(items);
	listViewByMonth.getSelectionModel().select(0);
    }
}
