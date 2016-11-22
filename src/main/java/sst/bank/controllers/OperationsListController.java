package sst.bank.controllers;

import java.time.LocalDate;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.controllers.utils.LocalDateStringConverter;
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

	formatDoubleColumn(amountColumn);
	formatLocalDateColumn(executionDateColumn);
	formatLocalDateColumn(valueDateColumn);

	tableView.setRowFactory(
		new Callback<TableView<OperationModel>, TableRow<OperationModel>>() {
		    @Override
		    public TableRow<OperationModel> call(TableView<OperationModel> tableView) {
			final TableRow<OperationModel> row = new TableRow<>();
			final ContextMenu rowMenu = new ContextMenu();
			MenuItem editItem = new MenuItem("Set Category...");
			// editItem.setOnAction(...);
			// MenuItem removeItem = new MenuItem("Delete");
			editItem.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {

			    }
			});
			rowMenu.getItems().addAll(editItem);

			// only display context menu for non-null items:
			row.contextMenuProperty().bind(
				Bindings.when(Bindings.isNotNull(row.itemProperty()))
					.then(rowMenu)
					.otherwise((ContextMenu) null));
			return row;
		    }
		});
    }

    private static Callback<TableColumn<OperationModel, Double>, TableCell<OperationModel, Double>> forTableColumn = TextFieldTableCell
	    .<OperationModel, Double> forTableColumn(new DoubleStringConverter());

    private void formatDoubleColumn(TableColumn<OperationModel, Double> column) {
	column.setCellFactory(forTableColumn);
	column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private static Callback<TableColumn<OperationModel, LocalDate>, TableCell<OperationModel, LocalDate>> forTableColumLocalDate = TextFieldTableCell
	    .<OperationModel, LocalDate> forTableColumn(new LocalDateStringConverter());

    private void formatLocalDateColumn(TableColumn<OperationModel, LocalDate> column) {
	column.setCellFactory(forTableColumLocalDate);
	// column.setStyle("-fx-alignment: CENTER-RIGHT;");
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
