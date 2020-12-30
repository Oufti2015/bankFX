package sst.bank.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.*;
import sst.bank.config.BankConfiguration;
import sst.bank.model.Category;
import sst.bank.model.OperationModel;
import sst.bank.model.container.BankContainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FXUtils {
    private FXUtils() {
    }

    public static void addMenuItem(TableView<OperationModel> tableView) {
        tableView.setRowFactory(
                tv -> {
                    final TableRow<OperationModel> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    setCategoryMenuItam(row, rowMenu);
                    copyCounterpartyMenuItem(row, rowMenu);

                    // only display context menu for non-null items:
                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu) null));
                    return row;
                });

    }

    private static void setCategoryMenuItam(TableRow<OperationModel> row, ContextMenu rowMenu) {
        Map<String, Category> catMap = new HashMap<>();
        Menu editItem = new Menu("Set Category based on Counterparty");
        BankContainer.me().getCategories().forEach(c -> {
            MenuItem subItem = new MenuItem(c.getFxName());
            catMap.put(c.getFxName(), c);

            subItem.setOnAction(event -> {
                Category category = catMap.get(((MenuItem) event.getSource()).getText());
                final OperationModel item = row.getItem();
                BankConfiguration.me().getCounterpartiesMapping().mappingPut(item.getCounterparty(), category);
                try {
                    BankConfiguration.me().getCounterpartiesMapping().save(BankConfiguration.me().getMappingFile(BankConfiguration.COUNTERPARTY_PROPERTIES));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            editItem.getItems().add(subItem);
        });

        rowMenu.getItems().addAll(editItem);
    }

    private static void copyCounterpartyMenuItem(TableRow<OperationModel> row, ContextMenu rowMenu) {
        MenuItem copyCounterpartyItem = new MenuItem("Copy Counterparty...");
        copyCounterpartyItem.setOnAction(event -> {
            ObjectProperty<OperationModel> operationModelObjectProperty = row.itemProperty();
            OperationModel operationModel = operationModelObjectProperty.get();
            BankClipboard.toClipboard(operationModel.getCounterparty());
        });
        rowMenu.getItems().addAll(copyCounterpartyItem);
    }
}
