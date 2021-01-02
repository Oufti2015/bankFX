package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.extern.log4j.Log4j2;
import sst.bank.controllers.utils.DoubleStringConverter;
import sst.bank.model.BankSummary;
import sst.bank.model.Budget.BudgetType;
import sst.bank.model.container.BankContainer;

import java.math.BigDecimal;

@Log4j2
public class TotalController {
    public static final String FX_ALIGNMENT_CENTER_RIGHT = "-fx-alignment: CENTER-RIGHT;";
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

        operationsLabel.setStyle(FX_ALIGNMENT_CENTER_RIGHT);
        operationsMonthLabel.setStyle(FX_ALIGNMENT_CENTER_RIGHT);
        totalRevenuesLabel.setStyle(FX_ALIGNMENT_CENTER_RIGHT);
        totalExpensesLabel.setStyle(FX_ALIGNMENT_CENTER_RIGHT);
        resultLabel.setStyle(FX_ALIGNMENT_CENTER_RIGHT);
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
        this.setOperations(BankContainer.me().operationsContainer().operations().size());
        this.setOperationsMonth(summary.operationsCount());

        Double revenues = summary.getList()
                .stream()
                .filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) > 0)
                .filter(o -> !o.getCategory().getBudget().getBudgetType().equals(BudgetType.SAVING))
                .mapToDouble(o -> o.getAmount().doubleValue())
                .sum();
        this.setRevenues(revenues);

        Double expenses = summary.getList()
                .stream()
                .filter(o -> o.getAmount().compareTo(BigDecimal.ZERO) < 0)
                .filter(s -> !s.getCategory().getBudget().getBudgetType().equals(BudgetType.SAVING))
                .mapToDouble(s -> s.getAmount().doubleValue())
                .sum();
        this.setExpenses(expenses);
        double result = revenues + expenses;
        this.setResult(result);
        this.setCreationDate(BankContainer.me().creationDate());
    }
}
