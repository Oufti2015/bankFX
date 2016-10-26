package sst.bank.controllers;

import java.time.Year;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    TreeView<String> treeView;
    @FXML
    OperationsListController operationsByMonthController;

    @FXML
    private void initialize() {
	log.info("initialize...");
    }

    public void setTitle(String title) {
	this.byLabel.setText(title);
    }

    public void setTreeViewData(List<BankSummary> list) {
	log.debug("operationsByMonthController=" + operationsByMonthController);
	log.debug("[setTreeViewData] list = " + list.size());
	String year = null;
	TreeItem<String> root = new TreeItem<String>("Calendar");
	TreeItem<String> currentTreeItem = null;
	for (BankSummary summary : list) {
	    if (year == null || !year.equals("" + Year.from(summary.getStartDate()))) {
		year = "" + Year.from(summary.getStartDate());
		currentTreeItem = new TreeItem<String>(year);
		root.getChildren().add(currentTreeItem);
	    }
	    currentTreeItem.getChildren()
		    .add(new TreeItem<String>(summary.getId()));
	}
	treeView.setRoot(root);
    }
}
