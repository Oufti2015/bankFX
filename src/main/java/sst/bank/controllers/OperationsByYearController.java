package sst.bank.controllers;

import sst.bank.utils.StringConstants;

public class OperationsByYearController extends OperationsController {
    @Override
    protected void setTitle() {
	byLabel.setText(StringConstants.BY_YEAR);
    }
}
