package sst.bank.controllers;

public class OperationsByYearController extends OperationsController {
    @Override
    protected void setTitle() {
	byLabel.setText("Op�rations group�es par ann�e");
    }
}
