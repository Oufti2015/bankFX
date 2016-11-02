package sst.bank.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j;
import sst.bank.OuftiBankFX;
import sst.bank.controllers.utils.CategoryComparator;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.SummaryModel;

@Log4j
public class SummaryListController {
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

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	log.info("initialize...");
	if (budgetTableView == null) {
	    log.fatal("budgetTableView is not initialised...");
	    OuftiBankFX.eventBus.post(new Exception("Controller not injected"));
	}
	if (sumCategoryColumn == null) {
	    log.fatal("sumCategoryColumn is not initialised...");
	    OuftiBankFX.eventBus.post(new Exception("Controller not injected"));
	}
	sumCategoryColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, String>("category"));
	sumAmountColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, Double>("amount"));
	sumBudgetColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, Double>("budget"));
	sumDiffColumn.setCellValueFactory(new PropertyValueFactory<SummaryModel, Double>("diff"));
	formatDoubleColumn(sumAmountColumn);
	formatDoubleColumn(sumBudgetColumn);
	formatDoubleColumn(sumDiffColumn);
    }

    public void setData(BankSummary newValue) {
	List<SummaryModel> list = new ArrayList<>();
	for (Category cat : newValue.getSummary().keySet()
		.stream()
		.sorted(new CategoryComparator())
		.collect(Collectors.toList())) {
	    SummaryModel e = new SummaryModel(cat, newValue.getSummary().get(cat).doubleValue());
	    list.add(e);
	    log.debug("" + cat + " : " + e + " " + e.getBudget());
	}

	budgetTableView.getItems().setAll(list);

    }

    private static Callback<TableColumn<SummaryModel, Double>, TableCell<SummaryModel, Double>> forTableColumnDouble = TextFieldTableCell
	    .<SummaryModel, Double> forTableColumn(new DoubleStringConverter());

    private void formatDoubleColumn(TableColumn<SummaryModel, Double> column) {
	column.setCellFactory(forTableColumnDouble);
	column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }
}
