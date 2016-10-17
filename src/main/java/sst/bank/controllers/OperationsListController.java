package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sst.bank.model.OperationModel;

public class OperationsListController {
    @FXML
    private TableView<OperationModel> tableView;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
}
