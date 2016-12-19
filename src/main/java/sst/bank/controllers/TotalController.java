package sst.bank.controllers;

import java.math.BigDecimal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.Budget.BudgetType;
import sst.bank.model.container.BankContainer;

@Log4j
public class TotalController {
    @FXML
    private Label operationsLabel;
    @FXML
    private Label operationsMonthLabel;
    @FXML
    private Label totalRevenuesLabel;
    @FXML
    private Label totalExpensesLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Label creationDateLabel;

    @FXML
    public void initialize() {
	log.debug("initialize...");

	operationsLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	operationsMonthLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	totalRevenuesLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	totalExpensesLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
	resultLabel.setStyle("-fx-alignment: CENTER-RIGHT;");
    }

    public void setOperations(int count) {
	operationsLabel.setText("" + count);
    }

    public void setOperationsMonth(int count) {
	operationsMonthLabel.setText("" + count);
    }

    public void setRevenues(Double amount) {
	totalRevenuesLabel.setText(new DoubleStringConverter().toString(amount));
    }

    public void setExpenses(Double amount) {
	totalExpensesLabel.setText(new DoubleStringConverter().toString(amount));
    }

    public void setResult(Double amount) {
	resultLabel.setText(new DoubleStringConverter().toString(amount));
    }

    public void setCreationDate(String date) {
	creationDateLabel.setText(date);
    }

    public void updateData(BankSummary summary) {
	this.setOperations(BankContainer.me().operations().size());
	this.setOperationsMonth(summary.operationsCount());
	Double revenues = summary.getSummary().values()
		.stream()
		.filter(s -> s.amount.compareTo(BigDecimal.ZERO) > 0)
		.filter(s -> !s.category.getBudget().getBudgetType().equals(BudgetType.SAVING))
		.mapToDouble(s -> s.amount.doubleValue())
		.sum();
	this.setRevenues(revenues);
	Double expenses = summary.getSummary().values()
		.stream()
		.filter(s -> s.amount.compareTo(BigDecimal.ZERO) < 0)
		.filter(s -> !s.category.getBudget().getBudgetType().equals(BudgetType.SAVING))
		.mapToDouble(s -> s.amount.doubleValue())
		.sum();
	this.setExpenses(expenses);
	double result = revenues + expenses;
	this.setResult(result);
	this.setCreationDate(BankContainer.me().getCreationDate());
    }
}
