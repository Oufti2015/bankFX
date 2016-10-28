package sst.bank.model;

import java.math.BigDecimal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SummaryModel {
    private StringProperty category;
    private DoubleProperty amount;
    private DoubleProperty budget;
    private DoubleProperty diff;

    public SummaryModel(Category category, double amount) {

	this.category = new SimpleStringProperty(category.getFxName());
	this.amount = new SimpleDoubleProperty(amount);
	BigDecimal budgetMonthlyControlledAmount = category.getBudget().monthlyControlledAmount();
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
