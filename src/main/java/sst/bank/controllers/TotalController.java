package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j;
import sst.bank.controllers.utils.DoubleStringConverter;

@Log4j
public class TotalController {
    @FXML
    private Label operationsLabel;
    @FXML
    private Label operationsMonthLabel;
    @FXML
    private Label totalOperationsLabel;
    @FXML
    private Label totalBudgetLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Label creationDateLabel;

    @FXML
    public void initialize() {
	log.debug("initialize...");

	operationsLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	operationsMonthLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	totalOperationsLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	totalBudgetLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	resultLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    public void setOperations(int count) {
	operationsLabel.setText("" + count);
    }

    public void setOperationsMonth(int count) {
	operationsMonthLabel.setText("" + count);
    }

    public void setTotal(Double amount) {
	totalOperationsLabel.setText(new DoubleStringConverter().toString(amount));
    }

    public void setBudget(Double amount) {
	totalBudgetLabel.setText(new DoubleStringConverter().toString(amount));
    }

    public void setResult(Double amount) {
	resultLabel.setText(new DoubleStringConverter().toString(amount));
    }

    public void setCreationDate(String date) {
	creationDateLabel.setText(date);
    }
}
