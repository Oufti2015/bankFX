package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j2;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.controllers.utils.LocalDateStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.OperationModel;
import sst.bank.utils.FXUtils;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Log4j2
public class OperationsListController {
    private static Callback<TableColumn<OperationModel, Double>, TableCell<OperationModel, Double>> forTableColumn = TextFieldTableCell
            .<OperationModel, Double>forTableColumn(new DoubleStringConverter());
    private static Callback<TableColumn<OperationModel, LocalDate>, TableCell<OperationModel, LocalDate>> forTableColumLocalDate = TextFieldTableCell
            .<OperationModel, LocalDate>forTableColumn(new LocalDateStringConverter());
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
        log.debug("initialize...");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("fortisId"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        valueDateColumn.setCellValueFactory(new PropertyValueFactory<>("valueDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        counterpartyColumn.setCellValueFactory(new PropertyValueFactory<>("counterparty"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));

        formatDoubleColumn(amountColumn);
        formatLocalDateColumn(valueDateColumn);

        FXUtils.addMenuItem(tableView);
    }

    private void formatDoubleColumn(TableColumn<OperationModel, Double> column) {
        column.setCellFactory(forTableColumn);
        column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void formatLocalDateColumn(TableColumn<OperationModel, LocalDate> column) {
        column.setCellFactory(forTableColumLocalDate);
    }

    public void setData(BankSummary bm) {
        tableView
                .getItems()
                .setAll(bm.getList()
                        .stream()
                        .map(OperationModel::new)
                        .collect(Collectors.toList()));
    }
}
