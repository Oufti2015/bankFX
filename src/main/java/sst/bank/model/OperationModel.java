package sst.bank.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OperationModel {
    private IntegerProperty bankId;
    private StringProperty fortisId;
    private ObjectProperty<LocalDate> executionDate;
    private ObjectProperty<LocalDate> valueDate;
    private DoubleProperty amount;
    private StringProperty currency;
    private StringProperty counterparty;
    private StringProperty detail;
    private StringProperty account;
    private StringProperty category;

    public OperationModel(Operation op) {
	this.bankId = new SimpleIntegerProperty(op.getBankId());
	this.fortisId = new SimpleStringProperty(
		op.getFortisId() != null ? op.getFortisId() : op.getBankId().toString());
	this.executionDate = new SimpleObjectProperty<LocalDate>(op.getExecutionDate());
	this.valueDate = new SimpleObjectProperty<LocalDate>(op.getValueDate());
	this.amount = new SimpleDoubleProperty(op.getAmount().doubleValue());
	this.counterparty = new SimpleStringProperty(op.getCounterparty());
	this.detail = new SimpleStringProperty(op.getDetail());
	this.category = new SimpleStringProperty(op.getCategory() == null ? "" : op.getCategory().getFxName());
    }

    public IntegerProperty bankId() {
	return bankId;
    }

    public StringProperty fortisId() {
	return fortisId;
    }

    public ObjectProperty<LocalDate> executionDate() {
	return executionDate;
    }

    public ObjectProperty<LocalDate> valueDate() {
	return valueDate;
    }

    public DoubleProperty amount() {
	return amount;
    }

    public StringProperty currency() {
	return currency;
    }

    public StringProperty counterparty() {
	return counterparty;
    }

    public StringProperty detail() {
	return detail;
    }

    public StringProperty account() {
	return account;
    }

    public StringProperty category() {
	return category;
    }

    public Integer getBankId() {
	return bankId.get();
    }

    public String getFortisId() {
	return fortisId.get();
    }

    public LocalDate getExecutionDate() {
	return executionDate.get();
    }

    public LocalDate getValueDate() {
	return valueDate.get();
    }

    public Double getAmount() {
	return amount.get();
    }

    public String getCurrency() {
	return currency.get();
    }

    public String getCounterparty() {
	return counterparty.get();
    }

    public String getDetail() {
	return detail.get();
    }

    public String getAccount() {
	return account.get();
    }

    public String getCategory() {
	return category.get();
    }

    public void setBankId(IntegerProperty bankId) {
	this.bankId = bankId;
    }

    public void setFortisId(StringProperty fortisId) {
	this.fortisId = fortisId;
    }

    public void setExecutionDate(ObjectProperty<LocalDate> executionDate) {
	this.executionDate = executionDate;
    }

    public void setValueDate(ObjectProperty<LocalDate> valueDate) {
	this.valueDate = valueDate;
    }

    public void setAmount(DoubleProperty amount) {
	this.amount = amount;
    }

    public void setCurrency(StringProperty currency) {
	this.currency = currency;
    }

    public void setCounterparty(StringProperty counterparty) {
	this.counterparty = counterparty;
    }

    public void setDetail(StringProperty detail) {
	this.detail = detail;
    }

    public void setAccount(StringProperty account) {
	this.account = account;
    }

    public void setCategory(StringProperty category) {
	this.category = category;
    }
}
