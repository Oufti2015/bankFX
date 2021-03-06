package sst.bank.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;
import sst.bank.controllers.utils.CategoryComparator;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.Category;
import sst.bank.model.Operation;
import sst.bank.model.container.BankContainer;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class OperationsByCategoryController {
    @FXML
    Accordion accordion;
    @FXML
    private Label byLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private ListView<Category> categoriesListView;

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
        categories.addAll(list
                .stream()
                .sorted(new CategoryComparator())
                .collect(Collectors.toList()));
        categoriesListView.setItems(categories);
        categoriesListView.setEditable(true);
        categoriesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        categoriesListView.getSelectionModel().selectedItemProperty().addListener(
                (ov, oldVal, newVal) -> {
                    log.debug("" + oldVal + "-" + newVal);
                    accordion.getPanes().clear();
                    BankSummary bankSummaryByCategory = BankContainer.me().getBankSummaryByCategory(newVal);
                    if (bankSummaryByCategory != null) {
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

                        yearList
                                .forEach(y -> {
                                    OperationsTableView olc = new OperationsTableView();
                                    List<Operation> opForOneYear = operationsList
                                            .stream()
                                            .filter(o -> Year.from(o.getValueDate()).equals(y))
                                            .sorted()
                                            .collect(Collectors.toList());
                                    double totalPerYear = opForOneYear
                                            .stream()
                                            .mapToDouble(o -> o.getAmount().doubleValue())
                                            .sum();
                                    TitledPane t1 = new TitledPane(y.toString() + " : "
                                            + new DoubleStringConverter().toString(totalPerYear), olc);
                                    olc.setData(opForOneYear);
                                    accordion.getPanes().add(t1);
                                });
                        String fromString = newVal.toString()
                                + " : "
                                + new DoubleStringConverter().toString(total);
                        fromLabel.setText(fromString);
                    }

                });
    }
}
