package sst.bank.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class SummaryModel {
    private final StringProperty category;
    private final DoubleProperty amount;
    private final DoubleProperty budget;
    private final DoubleProperty diff;

    public SummaryModel(Category category, double amount, int month) {

        this.category = new SimpleStringProperty(category.getFxName());
        this.amount = new SimpleDoubleProperty(amount);
        BigDecimal budgetMonthlyControlledAmount = category.getBudget().monthlyControlledAmount()
                .multiply(BigDecimal.valueOf(month));
        this.budget = new SimpleDoubleProperty(budgetMonthlyControlledAmount.doubleValue());

        BigDecimal subtractDiff = BigDecimal.valueOf(amount).subtract(budgetMonthlyControlledAmount);
        this.diff = new SimpleDoubleProperty(subtractDiff.doubleValue());
    }

    public StringProperty category() {
        return category;
    }

    public DoubleProperty amount() {
        return amount;
    }

    public DoubleProperty budget() {
        return budget;
    }

    public DoubleProperty diff() {
        return diff;
    }

    public String getCategory() {
        return category.get();
    }

    public Double getAmount() {
        return amount.get();
    }

    public Double getBudget() {
        return budget.get();
    }

    public Double getDiff() {
        return diff.get();
    }
}
