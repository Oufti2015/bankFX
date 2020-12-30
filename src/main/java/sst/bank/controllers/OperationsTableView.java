package sst.bank.controllers;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.controllers.utils.LocalDateStringConverter;
import sst.bank.model.Operation;
import sst.bank.model.OperationModel;
import sst.bank.utils.FXUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OperationsTableView extends TableView<OperationModel> {
    private static final Callback<TableColumn<OperationModel, Double>, TableCell<OperationModel, Double>> forTableColumn = TextFieldTableCell.forTableColumn(new DoubleStringConverter());
    private static final Callback<TableColumn<OperationModel, LocalDate>, TableCell<OperationModel, LocalDate>> forTableColumLocalDate = TextFieldTableCell.forTableColumn(new LocalDateStringConverter());

    public OperationsTableView() {
        TableColumn<OperationModel, String> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("fortisId"));
        this.getColumns().add(idColumn);

        TableColumn<OperationModel, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        this.getColumns().add(categoryColumn);

        TableColumn<OperationModel, LocalDate> valueDateColumn = new TableColumn<>("Value Date");
        valueDateColumn.setCellValueFactory(new PropertyValueFactory<>("valueDate"));
        this.getColumns().add(valueDateColumn);

        TableColumn<OperationModel, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        this.getColumns().add(amountColumn);

        TableColumn<OperationModel, String> counterpartyColumn = new TableColumn<>("Counterparty");
        counterpartyColumn.setCellValueFactory(new PropertyValueFactory<>("counterparty"));
        this.getColumns().add(counterpartyColumn);

        TableColumn<OperationModel, String> detailsColumn = new TableColumn<>("Details");
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("detail"));
        this.getColumns().add(detailsColumn);

        formatDoubleColumn(amountColumn);
        formatLocalDateColumn(valueDateColumn);
        FXUtils.addMenuItem(this);
    }

    private void formatDoubleColumn(TableColumn<OperationModel, Double> column) {
        column.setCellFactory(forTableColumn);
        column.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    private void formatLocalDateColumn(TableColumn<OperationModel, LocalDate> column) {
        column.setCellFactory(forTableColumLocalDate);
    }

    public void setData(List<Operation> list) {
        this.getItems().setAll(
                list.stream()
                        .map(OperationModel::new)
                        .collect(Collectors.toList()));
    }
}
