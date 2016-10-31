package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
	totalOperationsLabel.setText(String.format("%.2f €", amount));
    }

    public void setBudget(Double amount) {
	totalBudgetLabel.setText(String.format("%.2f €", amount));
    }

    public void setResult(Double amount) {
	resultLabel.setText(String.format("%.2f €", amount));
    }

    public void setCreationDate(String date) {
	creationDateLabel.setText(date);
    }
}
