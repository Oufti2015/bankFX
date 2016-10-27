package sst.bank.controllers;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j;
import sst.bank.model.BankSummary;
import sst.bank.model.OperationModel;

@Log4j
public class OperationsListController {
    @FXML
    private TableView<OperationModel> tableView;
    @FXML
    private TableColumn<OperationModel, String> idColumn;
    @FXML
    private TableColumn<OperationModel, String> categoryColumn;
    @FXML
    private TableColumn<OperationModel, LocalDate> executionDateColumn;
    @FXML
    private TableColumn<OperationModel, LocalDate> valueDateColumn;
    @FXML
    private TableColumn<OperationModel, Double> amountColumn;
    @FXML
    private TableColumn<OperationModel, String> counterpartyColumn;
    @FXML
    private TableColumn<OperationModel, String> detailsColumn;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	log.info("initialize...");
	idColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("fortisId"));
	categoryColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("category"));
	executionDateColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, LocalDate>("executionDate"));
	valueDateColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, LocalDate>("valueDate"));
	amountColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, Double>("amount"));
	counterpartyColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("counterparty"));
	detailsColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("detail"));
    }

    public void setData(BankSummary bm) {
	tableView
		.getItems()
		.setAll(bm.getList()
			.stream()
			.map(o -> new OperationModel(o))
			.collect(Collectors.toList()));
    }
}
