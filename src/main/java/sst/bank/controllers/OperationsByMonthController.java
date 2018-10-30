package sst.bank.controllers;

import javafx.fxml.FXML;
import sst.bank.model.BankSummary;
import sst.bank.utils.StringConstants;

public class OperationsByMonthController extends OperationsController {
    @FXML
    private OperationsListController operationsByMonthController;

    @Override
    protected void setTitle() {
        byLabel.setText(new String(StringConstants.BY_MONTH));
    }

    @Override
    protected void updateData(BankSummary summary) {
        super.updateData(summary);
        operationsByMonthController.setData(summary);
    }
}
