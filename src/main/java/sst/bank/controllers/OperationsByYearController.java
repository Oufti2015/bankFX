package sst.bank.controllers;

public class OperationsByYearController extends OperationsController {
    @Override
    protected void setTitle() {
	byLabel.setText("Opérations groupées par année");
    }
}
