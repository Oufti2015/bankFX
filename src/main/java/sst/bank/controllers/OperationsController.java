package sst.bank.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;
import sst.bank.controllers.utils.DescendingBankSummaryComparator;
import sst.bank.model.BankSummary;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
public class OperationsController {
    @FXML
    protected Label byLabel;
    @FXML
    protected Label fromLabel;
    @FXML
    protected Label summaryLabel;
    @FXML
    protected ListView<BankSummary> listView;
    @FXML
    protected TotalController totalController;
    @FXML
    protected SummaryPaneController summaryPaneController;

    @FXML
    protected void initialize() {
        log.debug("initialize...");
        setTitle();
        listView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateData(newValue)
        );
    }

    protected void updateData(BankSummary summary) {
        log.debug("Selected item: " + summary);
        fromLabel.setText(summary.toString());
        summaryPaneController.setData(summary);

        totalController.updateData(summary);
    }

    protected void setTitle() {
        byLabel.setText("Op�rations");
    }

    public void setListViewData(Collection<BankSummary> list) {
        ObservableList<BankSummary> items = FXCollections.observableArrayList();
        items.addAll(list
                .stream()
                .sorted(new DescendingBankSummaryComparator())
                .collect(Collectors.toList()));
        listView.setItems(items);
        listView.getSelectionModel().select(0);
    }
}
