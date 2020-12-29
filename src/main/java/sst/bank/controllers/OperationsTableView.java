package sst.bank.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.util.Callback;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.controllers.utils.LocalDateStringConverter;
import sst.bank.model.Operation;
import sst.bank.model.OperationModel;
import sst.bank.utils.BankClipboard;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OperationsTableView extends TableView<OperationModel> {
    private static Callback<TableColumn<OperationModel, Double>, TableCell<OperationModel, Double>> forTableColumn = TextFieldTableCell
            .<OperationModel, Double>forTableColumn(new DoubleStringConverter());
    private static Callback<TableColumn<OperationModel, LocalDate>, TableCell<OperationModel, LocalDate>> forTableColumLocalDate = TextFieldTableCell
            .<OperationModel, LocalDate>forTableColumn(new LocalDateStringConverter());
    private TableColumn<OperationModel, String> idColumn;
    private TableColumn<OperationModel, String> categoryColumn;
    // private TableColumn<OperationModel, LocalDate> executionDateColumn;
    private TableColumn<OperationModel, LocalDate> valueDateColumn;
    private TableColumn<OperationModel, Double> amountColumn;
    private TableColumn<OperationModel, String> counterpartyColumn;
    private TableColumn<OperationModel, String> detailsColumn;

    public OperationsTableView() {
        idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("fortisId"));
        this.getColumns().add(idColumn);
        categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("category"));
        this.getColumns().add(categoryColumn);
        // executionDateColumn.setCellValueFactory(new
        // PropertyValueFactory<OperationModel, LocalDate>("executionDate"));
        valueDateColumn = new TableColumn<>("Value Date");
        valueDateColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, LocalDate>("valueDate"));
        this.getColumns().add(valueDateColumn);
        amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, Double>("amount"));
        this.getColumns().add(amountColumn);
        counterpartyColumn = new TableColumn<>("Counterparty");
        counterpartyColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("counterparty"));
        this.getColumns().add(counterpartyColumn);
        detailsColumn = new TableColumn<>("Details");
        detailsColumn.setCellValueFactory(new PropertyValueFactory<OperationModel, String>("detail"));
        this.getColumns().add(detailsColumn);

        formatDoubleColumn(amountColumn);
        // formatLocalDateColumn(executionDateColumn);
        formatLocalDateColumn(valueDateColumn);
        setRowFactory(
                tableView -> {
                    final TableRow<OperationModel> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem("Set Category...");
                    // editItem.setOnAction(...);
                    // MenuItem removeItem = new MenuItem("Delete");
                    editItem.setOnAction(event -> {

                    });
                    rowMenu.getItems().addAll(editItem);
                    MenuItem copyCounterpartyItem = new MenuItem("Copy Counterparty...");
                    copyCounterpartyItem.setOnAction(event -> {
                        ObjectProperty<OperationModel> operationModelObjectProperty = row.itemProperty();
                        OperationModel operationModel = operationModelObjectProperty.get();
                        BankClipboard.toClipboard(operationModel.getCounterparty());
                    });
                    rowMenu.getItems().addAll(copyCounterpartyItem);

                    // only display context menu for non-null items:
                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu) null));
                    return row;
                });

    }

    private void formatDoubleColumn(TableColumn<OperationModel, Double> column) {
        column.setCellFactory(forTableColumn);
        column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void formatLocalDateColumn(TableColumn<OperationModel, LocalDate> column) {
        column.setCellFactory(forTableColumLocalDate);
        // column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    public void setData(List<Operation> list) {
        this.getItems().setAll(
                list.stream()
                        .map(o -> new OperationModel(o))
                        .collect(Collectors.toList()));
    }

}
