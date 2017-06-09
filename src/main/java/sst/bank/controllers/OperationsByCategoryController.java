package sst.bank.controllers;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import lombok.extern.log4j.Log4j;
import sst.bank.controllers.utils.CategoryComparator;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

@Log4j
public class OperationsByCategoryController {
    @FXML
    private Label byLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private ListView<Category> categoriesListView;
    @FXML
    Accordion accordion;

    @FXML
    private void initialize() {
	log.debug("initialize...");
	fromLabel.setText("");
    }

    public void setTitle(String title) {
	this.byLabel.setText(title);
    }

    public void setTreeViewData(Collection<Category> list) {
	ObservableList<Category> categories = FXCollections.observableArrayList();
	for (Category category : list
		.stream()
		.sorted(new CategoryComparator())
		.collect(Collectors.toList())) {
	    categories.add(category);
	}
	categoriesListView.setItems(categories);
	categoriesListView.setEditable(true);
	categoriesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	categoriesListView.getSelectionModel().selectedItemProperty().addListener(
		new ChangeListener<Category>() {
		    @Override
		    public void changed(ObservableValue<? extends Category> ov,
					Category old_val, Category new_val) {
			log.debug("" + old_val + "-" + new_val);
			BankSummary bankSummaryByCategory = BankContainer.me().getBankSummaryByCategory(new_val);
			final List<Operation> operationsList = bankSummaryByCategory.getList();
			double total = operationsList
				.stream()
				.mapToDouble(o -> o.getAmount().doubleValue())
				.sum();

			List<Year> yearList = operationsList
				.stream()
				.map(o -> Year.from(o.getValueDate()))
				.distinct()
				.sorted()
				.collect(Collectors.toList());

			accordion.getPanes().clear();

			yearList
				.stream()
				.forEach(y -> {
				    OperationsTableView olc = new OperationsTableView();
				    TitledPane t1 = new TitledPane(y.toString(), olc);
				    olc.setData(operationsList
					    .stream()
					    .filter(o -> Year.from(o.getValueDate()).equals(y))
					    .sorted()
					    .collect(Collectors.toList()));
				    accordion.getPanes().add(t1);
				});
			String fromString = new_val.toString()
				+ " : "
				+ new DoubleStringConverter().toString(total);
			fromLabel.setText(fromString);
		    }
		});
    }
}
