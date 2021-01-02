package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.controllers.utils.CategoryComparator;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.SummaryModel;
import sst.bank.model.container.BankContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SummaryListController {
    private static final Callback<TableColumn<SummaryModel, Double>, TableCell<SummaryModel, Double>> forTableColumnDouble
            = TextFieldTableCell.forTableColumn(new DoubleStringConverter());
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
        log.debug("initialize...");
        if (budgetTableView == null) {
            log.fatal("budgetTableView is not initialised...");
            OuftiBankFX.eventBus.post(new Exception("Controller not injected"));
        }
        if (sumCategoryColumn == null) {
            log.fatal("sumCategoryColumn is not initialised...");
            OuftiBankFX.eventBus.post(new Exception("Controller not injected"));
            return;
        }
        sumCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        sumAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        sumBudgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        sumDiffColumn.setCellValueFactory(new PropertyValueFactory<>("diff"));
        formatDoubleColumn(sumAmountColumn);
        formatDoubleColumn(sumBudgetColumn);
        formatDoubleColumn(sumDiffColumn);
    }

    public void setData(BankSummary newValue) {
        List<SummaryModel> list = new ArrayList<>();
        for (Category cat : newValue.getSummary().keySet()
                .stream()
                .map(c -> BankContainer.me().category(c.getName()))
                .sorted(new CategoryComparator())
                .collect(Collectors.toList())) {
            SummaryModel e = new SummaryModel(cat, newValue.getSummary().get(cat).amount.doubleValue(),
                    newValue.monthQuantity());
            list.add(e);
        }

        budgetTableView.getItems().setAll(list);
    }

    private void formatDoubleColumn(TableColumn<SummaryModel, Double> column) {
        column.setCellFactory(forTableColumnDouble);
        column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }
}
