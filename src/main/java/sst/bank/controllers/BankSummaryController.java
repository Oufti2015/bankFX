package sst.bank.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.SummaryModel;

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
    TableView<SummaryModel> budgetTableView;
    @FXML
    private TableColumn<SummaryModel, String> sumCategoryColumn;
    @FXML
    private TableColumn<SummaryModel, Double> sumAmountColumn;
    @FXML
    private TableColumn<SummaryModel, Double> sumBudgetColumn;
    @FXML
    private TableColumn<SummaryModel, Double> sumDiffColumn;

    @FXML
    private void initialize() {
	log.info("initialize...");
	if (budgetTableView == null) {
	    log.fatal("budgetTableView is not initialised...");
	    System.exit(-1);
	}
	if (sumCategoryColumn == null) {
	    log.fatal("sumCategoryColumn is not initialised...");
	    System.exit(-1);
	}
	sumCategoryColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, String>("category"));
	sumAmountColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, Double>("amount"));
	sumBudgetColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, Double>("budget"));
	sumDiffColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, Double>("diff"));

	listViewByMonth.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BankSummary>() {

	    @Override
	    public void changed(ObservableValue<? extends BankSummary> observable, BankSummary oldValue,
				BankSummary newValue) {
		// Your action here
		log.debug("Selected item: " + newValue);
		operationsByMonthController.setData(newValue);
		fromLabel.setText("From " + newValue.getStartDate() + " to " + newValue.getEndDate());

		List<SummaryModel> list = new ArrayList<>();
		for (Category cat : newValue.getSummary().keySet()) {
		    SummaryModel e = new SummaryModel(cat, newValue.getSummary().get(cat).doubleValue());
		    list.add(e);
		    log.debug("" + cat + " : " + e);
		}

		budgetTableView.getItems().setAll(list);
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
