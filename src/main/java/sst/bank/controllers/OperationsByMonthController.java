package sst.bank.controllers;

import javafx.fxml.FXML;
import sst.bank.model.BankSummary;

public class OperationsByMonthController extends OperationsController {
    @Override
    protected void setTitle() {
	byLabel.setText("Op�rations group�es par mois");
    }

    @FXML
    private OperationsListController operationsByMonthController;

    @Override
    protected void updateData(BankSummary summary) {
	super.updateData(summary);
	operationsByMonthController.setData(summary);
    }
}
